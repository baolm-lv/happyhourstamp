/*
 * Author: Luocheng Tang, DB config for master
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
        basePackages = "com.lvmama.vst.hhs.product.repository",
        entityManagerFactoryRef = "oracleEntityManager",
        transactionManagerRef = "oracleTransactionManager")
@PropertySource({ "classpath:db/oracle.properties" })
public class DBConnConfigLocalOracle {
    private Logger LOGGER = LoggerFactory.getLogger(DBConnConfigLocalOracle.class);

    private LocalContainerEntityManagerFactoryBean oracleEntityManager = null;
    
    @Value("${oracle.datasource.url}")
    private String url;
    @Value("${oracle.datasource.username}")
    private String username;
    @Value("${oracle.datasource.password}")
    private String password;
    @Value("${oracle.datasource.driverClassName}")
    private String driverClassName;
    
    @Bean(name = "oracleEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        if (null != this.oracleEntityManager) {
            return this.oracleEntityManager;
        }
        PoolProperties prop = new PoolProperties();
        prop.setUrl(this.url);
        prop.setDriverClassName(this.driverClassName);
        prop.setUsername(this.username);
        prop.setPassword(this.password); 
        // should new DataSource instead of autowired
        // because it is master db is already wired.
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(prop);
        this.LOGGER.info("dataSource:" + dataSource.toString());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[] { "com.lvmama.vst.hhs" //$NON-NLS-1$
        });

        JpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        this.oracleEntityManager = em;
        return em;
    }

    @Bean(name = "oracleTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
