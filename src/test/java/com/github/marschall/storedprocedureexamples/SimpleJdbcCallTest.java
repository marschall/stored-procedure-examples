package com.github.marschall.storedprocedureexamples;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class SimpleJdbcCallTest extends AbstractExampleTest {

  private SimpleJdbcCall jdbcCall;

  @BeforeEach
  public void setUp() {
    this.jdbcCall = new SimpleJdbcCall(getDataSource())
            .withProcedureName("plus1inout")
            .declareParameters( // required if you're not schema owner
                    new SqlParameter("arg", Types.INTEGER),
                    new SqlOutParameter("res", Types.INTEGER))
            .withoutProcedureColumnMetaDataAccess(); // required if you're not schema owner
  }

  @Override
  protected int plus1inout(int arg) throws SQLException {
    Map<String, Object> results = jdbcCall.execute(arg);
    return (Integer) results.get("res");
  }

}
