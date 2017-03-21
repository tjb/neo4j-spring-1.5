package com.example;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.core", transactionManagerRef = "neo4jTransactionManager")
@EnableTransactionManagement
@ConditionalOnProperty("spring.graph.enabled")
public class Neo4jConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.graph")
  public org.neo4j.ogm.config.Configuration getConfiguration() {
    org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
    config
        .driverConfiguration()
        .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
        .setURI("http://neo4j:root@localhost:7474");
    return config;
  }

  @Bean
  public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
    return new SessionFactory(configuration, "com.logicgate.**.**.graph");
  }

  @Bean
  public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
    return new Neo4jTransactionManager(sessionFactory);
  }

  @Autowired
  public PlatformTransactionManager transactionManager(Neo4jTransactionManager neo4jTransactionManager,
                                                       JpaTransactionManager transactionManager) {
    return new ChainedTransactionManager(
        transactionManager,
        neo4jTransactionManager
    );
  }
}