package com.github.marschall.storedprocedureexamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Disabled
public class Sql2OTest extends AbstractExampleTest {

  private Sql2o sql2o;

  @BeforeEach
  public void setUp() {
    this.sql2o = new Sql2o(this.getDataSource());
  }

  @Override
  protected int plus1inout(int arg) {
    String sql =
            "declare @outputParam as INTEGER " +
            "exec plus1inout @inputParam = :arg, @outputParam = @outputParam OUTPUT " +
            "select @outputParam as myval";

    try (Connection connection = this.sql2o.open()) {
      return connection.createQuery(sql)
                       .addParameter("arg", arg)
                       .executeScalar(Integer.class);
    }
  }

}
