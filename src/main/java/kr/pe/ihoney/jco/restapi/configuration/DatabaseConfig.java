package kr.pe.ihoney.jco.restapi.configuration;

import com.jolbox.bonecp.BoneCPDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 1:24
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"kr.pe.ihoney.jco.restapi.repository"})
@EnableTransactionManagement
public class DatabaseConfig {
    @Value("${database.password}")
    private String PROPERTY_DATABASE_PASSWORD;
    @Value("${database.username}")
    private String PROPERTY_DATABASE_USERNAME;
    @Value("${database.url}")
    private String PROPERTY_DATABASE_URL;
    @Value("${database.class}")
    private String PROPERTY_DATABASE_CLASS;
    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;
    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;
    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;
    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;
    
    private static final String SCAN_TARGET_PACKAGE = "kr.pe.ihoney.jco.restapi.domain";
    private static final String PERSISTENCE_UNIT_NAME = "rocking-the-rest-api";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(PROPERTY_DATABASE_CLASS);
        dataSource.setJdbcUrl(PROPERTY_DATABASE_URL);
        dataSource.setUser(PROPERTY_DATABASE_USERNAME);
        dataSource.setPassword(PROPERTY_DATABASE_PASSWORD);
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(420);
        dataSource.setMaxConnectionsPerPartition(30);
        dataSource.setMinConnectionsPerPartition(10);
        dataSource.setPartitionCount(3);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);
        
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        Properties jpaProperties = getJpaProperties();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        factory.setPackagesToScan(SCAN_TARGET_PACKAGE);
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();

        return factory;
    }

    private Properties getJpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, HIBERNATE_DIALECT);
        jpaProperties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);
        jpaProperties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, HIBERNATE_SHOW_SQL);
        jpaProperties.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, HIBERNATE_FORMAT_SQL);
        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
