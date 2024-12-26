package com.start.spring.springboot_tutorial.dao.db1.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:jpa-db1.properties"})
@NoArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.start.spring.springboot_tutorial.dao.db1.repository",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager")
public class UserDataSourceConfiguration {
    @Autowired
    private Environment env;

//    This also works
//    @Value("${spring.jpa.hibernate.ddl-auto}")
//    private String ddlAuto;

//    @Value("${spring.jpa.show-sql}")
//    private String showSql;


    // userEntityManager bean
    @Bean (name = "userEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean user
                = new LocalContainerEntityManagerFactoryBean();
        user.setDataSource(userDataSource());
        user.setPackagesToScan(
                new String[] { "com.start.spring.springboot_tutorial.dao.db1.entity" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        user.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();

        String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        String showSql = env.getProperty("spring.jpa.show-sql");
//        String databasePlatform =  env.getProperty("spring.jpa.database-platform");
        String generateDdl = env.getProperty("spring.jpa.generate-ddl");

        System.out.println("spring.jpa.hibernate.ddl-auto:" +  ddlAuto);
        System.out.println("spring.jpa.show-sql:" +  showSql);

        properties.put("spring.jpa.hibernate.ddl-auto",  ddlAuto);
        properties.put("spring.jpa.show-sql", showSql);
//        properties.put("spring.jpa.database-platform", databasePlatform);
//        properties.put("spring.jpa.generate-ddl", generateDdl);

        user.setJpaPropertyMap(properties);

        return user;
    }

//    @ConfigurationProperties(prefix="spring.db1.datasource")
//    @Bean
//    public DataSourceProperties userDataSourceProperties() {
//        return new DataSourceProperties();
//    }


    @Primary
    @Bean(name="userDataSource")
//    This also works
//    @ConfigurationProperties("spring.db1.datasource")
    @ConfigurationProperties(prefix="spring.db1.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();

//    This should work, but not here
//    return userDataSourceProperties().initializeDataSourceBuilder().build();
    }

    // userTransactionManager bean
    @Primary
    @Bean (name="userTransactionManager")
    public PlatformTransactionManager userTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                userEntityManager().getObject());
        return transactionManager;
    }



}
