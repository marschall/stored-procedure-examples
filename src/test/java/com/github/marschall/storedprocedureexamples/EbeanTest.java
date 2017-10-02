package com.github.marschall.storedprocedureexamples;

import java.sql.Types;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.ebean.CallableSql;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;

public class EbeanTest extends AbstractExampleTest {

  private EbeanServer server;

  @BeforeEach
  public void setUp() {
    ServerConfig config = new ServerConfig();
    config.setName("ebeantest");
    config.setDisableClasspathSearch(true);
    config.setDataSource(this.getDataSource());
    config.setDefaultServer(true);

    server = EbeanServerFactory.create(config);
  }

  @AfterEach
  public void tearDown() {
    server.shutdown(false, false);
  }

  @Override
  protected int plus1inout(int arg) {
    String sql = "{call plus1inout(?,?)}";

    CallableSql callableSql = Ebean.createCallableSql(sql);
    callableSql.setParameter(1, arg);
    callableSql.registerOut(2, Types.INTEGER);

    Ebean.execute(callableSql);
    return (int) callableSql.getObject(2);
  }

}
