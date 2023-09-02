package com.example.privatehospital.Configs;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.core.parameters.P;

@EnableJpaRepositories(basePackages = {
        "com.example.privatehospital.Repositories"
})
@Configuration
@ComponentScan
public class DbConfig {
    @Bean
    public JdbcTemplate getTemplate(){
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/PrivateHospital");
        ds.setUser("postgres");
        ds.setPassword("wtf2281337");
        return new JdbcTemplate(ds);
    }
}
