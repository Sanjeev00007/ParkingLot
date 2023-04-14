package com.example.parking.data.dataStore;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/sanjeevp")   //driverclass:databasename/actualurl/databasename, 3306 is default port o for mysql
                .username("root")
                .password("Qwerf@34%")
                .build();
     }
//since its a bean it can be used everywhere using @autowired, but mostly this configuration is not used by us instead
//spring data itself, we will be using entitymanager.

}
