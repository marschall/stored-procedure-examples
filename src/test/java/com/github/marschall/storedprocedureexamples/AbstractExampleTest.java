package com.github.marschall.storedprocedureexamples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.github.marschall.storedprocedureexamples.configuration.HsqlConfiguration;
import com.github.marschall.storedprocedureexamples.configuration.TestConfiguration;

@Transactional
@SpringJUnitConfig({HsqlConfiguration.class, TestConfiguration.class})
public abstract class AbstractExampleTest {

  @Autowired
  private DataSource dataSource;

  protected DataSource getDataSource() {
    return this.dataSource;
  }

  @Test
  public void callProcedure() throws SQLException {
    assertEquals(2, this.plus1inout(1));
  }

  protected abstract int plus1inout(int arg) throws SQLException;

}
