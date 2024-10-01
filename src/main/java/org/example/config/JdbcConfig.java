package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.example")
public class JdbcConfig {
    static ConfigLoader config = new ConfigLoader();

    @Bean
    public DataSource dataSource() throws IOException {
        // Properties props = new Properties();
        // props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(config.getProperty("jdbc.url"));
        dataSource.setUsername(config.getProperty("jdbc.username"));
        dataSource.setPassword(config.getProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
