package com.github.marschall.storedprocedureexamples;

import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.datanucleus.api.jdo.JDOQuery;
import org.datanucleus.metadata.StoredProcQueryParameterMode;
import org.datanucleus.store.rdbms.query.StoredProcedureQuery;
import org.junit.Before;

public class DatanucleusTest extends AbstractExampleTest {

  private PersistenceManager persistenceManager;

  @Before
  public void setUp() {
    Properties properties = new Properties();
    properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
    properties.put("datanucleus.ConnectionFactory", getDataSource());
    PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory(properties);

    this.persistenceManager = factory.getPersistenceManager();
  }

  @Override
  protected int plus1inout(int arg) {
    // http://www.datanucleus.org/products/datanucleus/jdo/stored_procedures.html
    Query<?> query = persistenceManager.newQuery("STOREDPROC", "plus1inout");
    StoredProcedureQuery storedProcedure = (StoredProcedureQuery) ((JDOQuery<?>) query).getInternalQuery();

    storedProcedure.registerParameter(1, Integer.class, StoredProcQueryParameterMode.IN);
    storedProcedure.registerParameter(2, Integer.class, StoredProcQueryParameterMode.OUT);

    storedProcedure.executeWithArray(new Object[] {arg}); // no varargs
    return (int) storedProcedure.getNextResults();
  }

}
