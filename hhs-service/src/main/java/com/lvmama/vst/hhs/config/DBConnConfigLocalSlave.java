/*
 * Author: Luocheng Tang
 * Create a new Spring transaction manager for Slave DB to support 
 * read and write separation.
 */
package com.lvmama.vst.hhs.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
/*
 *  make sure basePackages and entityManagerFactoryRef point to 
 *  exactly and correct values !!!
 */
@EnableJpaRepositories(
        basePackages = "com.lvmama.vst.hhs.stampDefinition.repositorySlave",
        entityManagerFactoryRef = "slaveEntityManager",
        transactionManagerRef = "slaveTransactionManager")
@PropertySource({ "classpath:db/mysql-slave.properties" })
public class DBConnConfigLocalSlave {
    private Logger LOGGER = LoggerFactory.getLogger(DBConnConfigLocalSlave.class);
    
    @Value("${slave.datasource.url}")
    private String url;
    @Value("${slave.datasource.username}")
    private String username;
    @Value("${slave.datasource.password}")
    private String password;
    @Value("${slave.datasource.driverClassName}")
    private String driverClassName;
    @Value("${slave.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${slave.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${slave.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${slave.datasource.validationQuery}")
    private String validationQuery;
    @Value("${slave.datasource.removeAbandoned}")
    private boolean removeAbandoned;
    @Value("${slave.datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;
    
    private LocalContainerEntityManagerFactoryBean entityManager1;

    @Bean(name = "slaveEntityManager")
    public LocalContainerEntityManagerFactoryBean slaveEntityManager() {
        if (null != this.entityManager1) {
            return this.entityManager1;
        }
        PoolProperties prop = new PoolProperties();
        prop.setUrl(this.url);
        prop.setDriverClassName(this.driverClassName);
        prop.setUsername(this.username);
        prop.setPassword(this.password); 
        prop.setTestWhileIdle(this.testWhileIdle);
        prop.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        prop.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        prop.setValidationQuery(this.validationQuery);
        prop.setRemoveAbandoned(this.removeAbandoned);
        prop.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
        // should new DataSource instead of autowired
        // because it is master db is already wired.
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(prop);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(30000); //30 seconds
        dataSource.setMinEvictableIdleTimeMillis(300000);	//5 minutes
        dataSource.setValidationQuery("SELECT 1");
        this.LOGGER.info("dataSource:" + dataSource.toString());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[] { "com.lvmama.vst.hhs" //$NON-NLS-1$
        });

        JpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        this.entityManager1 = em;
        return em;
    }
     
    // name is very important here.
    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager slaveTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(slaveEntityManager().getObject());
        return transactionManager;
    }
}
