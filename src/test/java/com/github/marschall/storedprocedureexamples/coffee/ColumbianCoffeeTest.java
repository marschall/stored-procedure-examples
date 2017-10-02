package com.github.marschall.storedprocedureexamples.coffee;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_COMMENT_PREFIX;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.github.marschall.storedprocedureexamples.configuration.MysqlConfiguration;
import com.github.marschall.storedprocedureproxy.ProcedureCallerFactory;
import com.github.marschall.storedprocedureproxy.spi.NamingStrategy;

@Transactional
@SpringJUnitConfig(MysqlConfiguration.class)
public class ColumbianCoffeeTest {

  @Autowired
  private DataSource dataSource;

  private Coffee cofffee;

  private JdbcOperations jdbc;

  @BeforeEach
  public void setUp() throws SQLException {
    try (Connection connection = this.dataSource.getConnection()) {
      // execute the set up SQL scripts
      ScriptUtils.executeSqlScript(connection, script("create-tables.sql"));
      ScriptUtils.executeSqlScript(connection, script("populate-tables.sql"));
      ScriptUtils.executeSqlScript(connection, script("create-procedures.sql"),
              false, false,
              DEFAULT_COMMENT_PREFIX, "|",
              DEFAULT_BLOCK_COMMENT_START_DELIMITER, DEFAULT_BLOCK_COMMENT_END_DELIMITER);
    }

    // create the instance of the Coffee interface
    this.cofffee = ProcedureCallerFactory.of(Coffee.class, this.dataSource)
            .withProcedureNamingStrategy(NamingStrategy.snakeCase().thenUpperCase())
            .build();

    // create a JdbcTemplate for selecting
    this.jdbc = new JdbcTemplate(this.dataSource);
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection connection = this.dataSource.getConnection()) {
      // execute the tear down SQL script
      ScriptUtils.executeSqlScript(connection, script("drop-tables.sql"));
    }
  }

  private static EncodedResource script(String path) {
    return new EncodedResource(new ClassPathResource("colombian_coffee/mysql/" + path), US_ASCII);
  }

  // see StoredProcedureMySQLSample.main
  @Test
  public void runStoredProcedures() {
    runStoredProcedures("Colombian", 0.10f, new BigDecimal("19.99"));
  }

  // see StoredProcedureMySQLSample.runStoredProcedures
  public void runStoredProcedures(String coffeeNameArg, float maximumPercentageArg, BigDecimal newPriceArg) {

    String supplierName = this.cofffee.getSupplierOfCoffee(coffeeNameArg);

    if (supplierName != null) {
      System.out.println("\nSupplier of the coffee " + coffeeNameArg + ": " + supplierName);
    } else {
      System.out.println("\nUnable to find the coffee " + coffeeNameArg);
    }

    List<CoffeeSupplier> suppliers = this.cofffee.showSuppliers(rs -> new CoffeeSupplier(rs.getString(1), rs.getString(2)));
    for (CoffeeSupplier supplier : suppliers) {
      System.out.println(supplier.getName() + ": " + supplier.getCoffee());
    }

    System.out.println("\nContents of COFFEES table before calling RAISE_PRICE:");
    this.viewTable();

    BigDecimal newPrice = this.cofffee.raisePrice(coffeeNameArg, maximumPercentageArg, newPriceArg);
    System.out.println("\nValue of newPrice after calling RAISE_PRICE: " + newPrice);

    System.out.println("\nContents of COFFEES table after calling RAISE_PRICE:");
    this.viewTable();
  }

  private void viewTable() {
    this.jdbc.query("select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES", rs -> {
      String coffeeName = rs.getString("COF_NAME");
      int supplierID = rs.getInt("SUP_ID");
      float price = rs.getFloat("PRICE");
      int sales = rs.getInt("SALES");
      int total = rs.getInt("TOTAL");
      System.out.println(coffeeName + ", " + supplierID + ", " + price +
              ", " + sales + ", " + total);
    });
  }


}
