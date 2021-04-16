package com.github.marschall.storedprocedureexamples;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

@Disabled("broken")
public class MybatisTest extends AbstractExampleTest {

  // https://www.programmersought.com/article/4495431825/

  private SqlSessionFactory sqlSessionFactory;

  @BeforeEach
  public void setUp() {
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, this.getDataSource());
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(Procedures.class);
    this.sqlSessionFactory = new SqlSessionFactoryBuilder()
            .build(configuration);
  }

  @Override
  protected int plus1inout(int arg) throws SQLException {
    try (SqlSession session = this.sqlSessionFactory.openSession()) {
      Procedures mapper = session.getMapper(Procedures.class);
      return mapper.plus1inout(arg);
    }
  }

  public interface Procedures {

    @Select("{call plus1inout(#{arg, jdbcType = INTEGER, mode = IN}, #{res, jdbcType = INTEGER, mode = OUT})}")
    @ResultType(Integer.class)
    @Options(statementType = StatementType.CALLABLE)
    Integer plus1inout(@Param("arg") Integer arg);
  }

}
