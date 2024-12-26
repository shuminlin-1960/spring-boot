package com.start.spring.springboot_tutorial.dao.db2.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:jpa-db2.properties"})
@NoArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.start.spring.springboot_tutorial.dao.db2.repository",
        entityManagerFactoryRef = "departmentEntityManager",
        transactionManagerRef = "departmentTransactionManager")
public class DepartmentDataSourceConfiguration {
//    @Autowired
//    private Environment env;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    @Value("${spring.jpa.show-sql}")
    private String showSql;


//    @Value("${spring.jpa.database-platform}")
//    private String databasePlatform;

    @Value("${spring.jpa.generate-ddl}")
    private String generateDdl;

    @Bean (name = "departmentEntityManager")
    public LocalContainerEntityManagerFactoryBean departmentEntityManager() {
        LocalContainerEntityManagerFactoryBean dept
                = new LocalContainerEntityManagerFactoryBean();
        dept.setDataSource(departmentDataSource());
        dept.setPackagesToScan(
                new String[] { "com.start.spring.springboot_tutorial.dao.db2.entity" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        dept.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        System.out.println("spring.jpa.hibernate.ddl-auto:" +  ddlAuto);
        System.out.println("spring.jpa.show-sql:" +  showSql);

        properties.put("spring.jpa.hibernate.ddl-auto",  ddlAuto);
        properties.put("spring.jpa.show-sql", showSql);
//        properties.put("spring.jpa.database-platform", databasePlatform);
//        properties.put("spring.jpa.generate-ddl", generateDdl);
//        properties.put("spring.jpa.database-platform", hibernateDialect);

        dept.setJpaPropertyMap(properties);

        return dept;
    }


//    @ConfigurationProperties(prefix="spring.db2.datasource")
//    @Bean
//    public DataSourceProperties departmentDataSourceProperties() {
//        return new DataSourceProperties();
//    }

    @Bean (name = "departmentDataSource")
//    This also works
//    @ConfigurationProperties("spring.db2.datasource")
    @ConfigurationProperties(prefix="spring.db2.datasource")
    public DataSource departmentDataSource() {
        return DataSourceBuilder.create().build();

//    This should work, but not here
//    return departmentDataSourceProperties().initializeDataSourceBuilder().build();
    }

    // departmentTransactionManager bean
    @Bean(name="departmentTransactionManager")
    public PlatformTransactionManager departmentTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                departmentEntityManager().getObject());
        return transactionManager;
    }
}
