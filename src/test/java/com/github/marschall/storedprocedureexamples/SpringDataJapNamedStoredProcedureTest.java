package com.github.marschall.storedprocedureexamples;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class SpringDataJapNamedStoredProcedureTest extends AbstractExampleTest {

  @Autowired
  private UserRepository repository;

  /**
   * @see <a href="https://jira.spring.io/browse/DATAJPA-455">DATAJPA-455</a>
   */
  @Override
  protected int plus1inout(int arg) {
    return repository.plus1BackedByOtherNamedStoredProcedure(1);
  }

}
