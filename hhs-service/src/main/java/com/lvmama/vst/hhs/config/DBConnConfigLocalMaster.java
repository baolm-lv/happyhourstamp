/*
 * Author: Luocheng Tang, DB config for master
 */
package com.lvmama.vst.hhs.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.lvmama.vst.hhs.stampDefinition.repository",
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager")
public class DBConnConfigLocalMaster {
    private Logger LOGGER = LoggerFactory.getLogger(DBConnConfigLocalMaster.class);

    @Autowired
    DataSource dataSource;
    private LocalContainerEntityManagerFactoryBean entityManager1 = null;

    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        if (null != this.entityManager1) {
            return this.entityManager1;
        }
        this.LOGGER.info("dataSource:" + this.dataSource.toString());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSource);
        em.setPackagesToScan(new String[] { "com.lvmama.vst.hhs" //$NON-NLS-1$
        });

        JpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        this.entityManager1 = em;
        return em;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
