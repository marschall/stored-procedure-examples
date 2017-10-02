package com.github.marschall.storedprocedureexamples;

import java.sql.Types;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.OutParameters;

public class JdbiTest extends AbstractExampleTest {

  private Handle handle;

  @BeforeEach
  public void setUp() {
    DBI dbi = new DBI(this.getDataSource());
    this.handle = dbi.open();
  }

  @AfterEach
  public void tearDown() {
    this.handle.close();
  }

  @Override
  protected int plus1inout(int arg) {
    OutParameters outParameters = handle.createCall("call plus1inout(?, ?);")
            .bind(0, arg)
            .registerOutParameter(1, Types.INTEGER)
            .invoke();
    return outParameters.getInt(2);
  }

}
