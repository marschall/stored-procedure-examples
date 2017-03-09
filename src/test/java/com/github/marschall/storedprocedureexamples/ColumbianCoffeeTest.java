package com.github.marschall.storedprocedureexamples;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_COMMENT_PREFIX;
import static org.springframework.jdbc.datasource.init.ScriptUtils.DEFAULT_STATEMENT_SEPARATOR;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.transaction.annotation.Transactional;

import com.github.marschall.storedprocedureexamples.configuration.MysqlConfiguration;

@Transactional
@ContextConfiguration(classes = MysqlConfiguration.class)
public class ColumbianCoffeeTest {

  @ClassRule
  public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();

  @Autowired
  private DataSource dataSource;

  @Before
  public void setUp() throws SQLException {
    try (Connection connection = this.dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(connection, script("create-tables.sql"));
      ScriptUtils.executeSqlScript(connection, script("populate-tables.sql"));
      ScriptUtils.executeSqlScript(connection, script("create-procedures.sql"),
              false, false,
              DEFAULT_COMMENT_PREFIX, "|",
              DEFAULT_BLOCK_COMMENT_START_DELIMITER, DEFAULT_BLOCK_COMMENT_END_DELIMITER);
    }
  }

  @After
  public void tearDown() throws SQLException {
    try (Connection connection = this.dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(connection, script("drop-tables.sql"));
    }
  }

  private static EncodedResource script(String path) {
    return new EncodedResource(new ClassPathResource("colombian_coffee/mysql/" + path), US_ASCII);
  }

  @Test
  public void empty() {

  }

}
