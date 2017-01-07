package com.github.marschall.storedprocedureexamples.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.marschall.storedprocedureexamples.UserRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class TestConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(this.dataSource);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setPersistenceUnitName("stored-procedure-hibernate");
    bean.setJpaDialect(jpaDialect());
    bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    bean.setDataSource(dataSource);
    return bean;
  }

  @Bean
  public JpaDialect jpaDialect() {
    return new HibernateJpaDialect();
  }

}
