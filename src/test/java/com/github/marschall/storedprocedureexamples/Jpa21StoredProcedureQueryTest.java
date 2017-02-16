package com.github.marschall.storedprocedureexamples;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;

public class Jpa21StoredProcedureQueryTest extends AbstractExampleTest {

  @Autowired
  private EntityManager entityManager;

  @Override
  protected int plus1inout(int arg) {

    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("plus1inout")
      .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
      .registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT)
      .setParameter(1, arg);
    query.execute();

    return (int) query.getOutputParameterValue(2);
  }

}
