package com.github.marschall.storedprocedureexamples;

import java.sql.SQLException;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.QueryResponse;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ProcedureCall;
import org.apache.cayenne.query.ProcedureQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

@Disabled("broken")
public class CayenneTest extends AbstractExampleTest {

//  private DataContext context;
  private ObjectContext context;

  @BeforeEach
  void setUp() {
    ServerRuntime cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-project.xml")
        .dataSource(this.getDataSource())
        .build();
    this.context = cayenneRuntime.newContext();
  }

  @Override
  protected int plus1inout(int arg) throws SQLException {
    // https://dev.to/williamlake/importing-stored-procedures-in-apache-cayenne-560b

    ProcedureCall
    .query("plus1inout",Integer.class)
    .param("arg", arg)
    .call(this.context) // The previously created context.
    .firstList();


    ProcedureQuery query = new ProcedureQuery("plus1inout", Integer.class);

    query.addParameter("arg", arg);

    QueryResponse result = this.context.performGenericQuery(query);
    List outList = result.firstList();
    return (int) outList.get(0);
  }

}
