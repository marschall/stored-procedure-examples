package com.github.marschall.storedprocedureexamples;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;

public class Jpa21NamedStoredProcedureQueryTest extends AbstractExampleTest {

  @Autowired
  private EntityManager entityManager;

  @Override
  protected int plus1inout(int arg) {
    StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("User.plus1");

    query.setParameter("arg", arg);
    query.execute();

    return (int) query.getOutputParameterValue("res");
  }

}
