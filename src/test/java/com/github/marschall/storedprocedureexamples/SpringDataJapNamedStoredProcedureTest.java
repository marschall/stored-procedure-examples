package com.github.marschall.storedprocedureexamples;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

public class SpringDataJapNamedStoredProcedureTest extends AbstractExampleTest {

  @Autowired
  private UserRepository repository;

  /**
   * @see <a href="https://jira.spring.io/browse/DATAJPA-455">DATAJPA-455</a>
   */
  @Override
  protected int plus1inout(int arg) throws SQLException {
    return repository.plus1BackedByOtherNamedStoredProcedure(1);
  }

}
