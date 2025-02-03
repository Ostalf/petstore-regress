package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.example.db.PetstoreDBQueries;
import org.example.util.ApplicationPropertySources;
import org.openapitools.client.api.CategoryControllerApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ApplicationPropertySources
@EnableConfigurationProperties

public class PetstoreConfiguration {

    @Bean
    public Feign.Builder jackson (){
        return Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder(new ObjectMapper())))
                .decoder(new JacksonDecoder(new ObjectMapper()))
                .logLevel(Logger.Level.BASIC)
                .logger(new Slf4jLogger())
                .retryer(Retryer.NEVER_RETRY)
                .client(new OkHttpClient());
    }


    @Bean
    public DataSource petstoreDataSource(
            @Value("${datasource.jdbc-url}") String jdbcUrl,
            @Value("${datasource.username}") String username,
            @Value("${datasource.password}") String password,
            @Value("${datasource.driver-class-name}") String driverClassName
    ) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate petstoreJdbcTemplate(DataSource petstoreDataSource) {
        return new JdbcTemplate(petstoreDataSource);
    }

    @Bean
    public PetstoreDBQueries petstoreDBQueries() {
       return new PetstoreDBQueries();
    }

    @Bean
    public CategoryControllerApi controllerApi(
            Feign.Builder builder,
            @Value("${service.url}") String url) {
        return builder.target(CategoryControllerApi.class, url);
    }
}
