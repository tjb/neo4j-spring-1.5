package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.core")
@EnableAutoConfiguration(exclude = {Neo4jDataAutoConfiguration.class, Neo4jRepositoriesAutoConfiguration.class})
@EnableTransactionManagement
public class MySqlConfig {

  @Primary
  @Bean(name = "dataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder
        .create()
        .driverClassName("com.mysql.jdbc.Driver")
        .build();
  }

  @Autowired
  @Bean(name = "transactionManager")
  @Primary
  public JpaTransactionManager mySqlTransactionManager(LocalContainerEntityManagerFactoryBean entityManageFactory)
      throws Exception {
    return new JpaTransactionManager(entityManageFactory.getObject());
  }
}
