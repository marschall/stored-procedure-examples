package com.github.marschall.storedprocedureexamples;

import java.sql.Types;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.OutParameters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JdbiTest extends AbstractExampleTest {

  private Handle handle;

  @BeforeEach
  public void setUp() {
    Jdbi dbi = Jdbi.create(this.getDataSource());
    this.handle = dbi.open();
  }

  @AfterEach
  public void tearDown() {
    this.handle.close();
  }

  @Override
  protected int plus1inout(int arg) {
    OutParameters outParameters = this.handle.createCall("call plus1inout(?, ?);")
            .bind(0, arg)
            .registerOutParameter(1, Types.INTEGER)
            .invoke();
    return outParameters.getInt(1);
  }

}
