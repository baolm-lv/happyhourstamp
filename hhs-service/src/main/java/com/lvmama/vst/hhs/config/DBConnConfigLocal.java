package com.lvmama.vst.hhs.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Profile({"unit-test", "integration-test"})
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.lvmama.vst")
public class DBConnConfigLocal {
    private Logger LOGGER = LoggerFactory.getLogger(DBConnConfigLocal.class);
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
        this.LOGGER.info("dataSource:" + dataSource.toString());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[] { "com.lvmama.vst.hhs" //$NON-NLS-1$
        });

        JpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    } 

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}
