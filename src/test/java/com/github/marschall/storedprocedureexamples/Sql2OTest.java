package com.github.marschall.storedprocedureexamples;

import org.junit.Before;
import org.junit.Ignore;
import org.sql2o.Sql2o;

@Ignore
public class Sql2OTest extends AbstractExampleTest {

  private Sql2o sql2o;

  @Before
  public void setUp() {
    this.sql2o = new Sql2o(getDataSource());
  }

  @Override
  protected int plus1inout(int arg) {
    String sql =
            "declare @outputParam as INTEGER " +
            "exec plus1inout @inputParam = :arg, @outputParam = @outputParam OUTPUT " +
            "select @outputParam as myval";

    return sql2o.createQuery(sql).addParameter("arg", arg).executeScalar(Integer.class);
  }

}
