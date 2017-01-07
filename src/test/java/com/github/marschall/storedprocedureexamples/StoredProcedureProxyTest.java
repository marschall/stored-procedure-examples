package com.github.marschall.storedprocedureexamples;

import org.junit.Before;

import com.github.marschall.storedprocedureproxy.ProcedureCallerFactory;
import com.github.marschall.storedprocedureproxy.annotations.OutParameter;

public class StoredProcedureProxyTest extends AbstractExampleTest {

  private Procedures procedures;

  @Before
  public void setUp() {
    this.procedures = ProcedureCallerFactory.build(Procedures.class, this.getDataSource());
  }

  @Override
  protected int plus1inout(int arg) {
    return this.procedures.plus1inout(arg);
  }

  interface Procedures {

    @OutParameter
    int plus1inout(int arg);

  }

}
