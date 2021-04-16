package com.github.marschall.storedprocedureexamples;

import java.sql.Types;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.ebean.CallableSql;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;

public class EbeanTest extends AbstractExampleTest {

  private Database database;

  @BeforeEach
  public void setUp() {
    DatabaseConfig config = new DatabaseConfig();
    config.setName("ebeantest");
    config.setDisableClasspathSearch(true);
    config.setDataSource(this.getDataSource());
    config.setDefaultServer(true);

    this.database = DatabaseFactory.create(config);
  }

  @AfterEach
  public void tearDown() {
    this.database.shutdown(false, false);
  }

  @Override
  protected int plus1inout(int arg) {
    String sql = "{call plus1inout(?,?)}";

    CallableSql callableSql = this.database.createCallableSql(sql);
    callableSql.setParameter(1, arg);
    callableSql.registerOut(2, Types.INTEGER);

    this.database.execute(callableSql);
    return (int) callableSql.getObject(2);
  }

}
