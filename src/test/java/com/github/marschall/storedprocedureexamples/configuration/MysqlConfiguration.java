package com.github.marschall.storedprocedureexamples.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MysqlConfiguration {

  @Bean
  public DataSource dataSource() {
    SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    dataSource.setSuppressClose(true);
    String userName = System.getProperty("user.name");
    String database = userName;
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
    dataSource.setUrl("jdbc:mysql://localhost:3306/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&logger=com.mysql.cj.log.Slf4JLogger");
    dataSource.setUsername(userName);
    String password = this.isTravis() ? "" : userName;
    dataSource.setPassword(password);
    return dataSource;
  }

  private boolean isTravis() {
    return System.getenv().getOrDefault("TRAVIS", "false").equals("true");
  }

  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(this.dataSource());
  }

}
