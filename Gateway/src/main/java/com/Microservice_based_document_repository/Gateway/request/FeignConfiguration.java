package com.Microservice_based_document_repository.Gateway.request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures OpenFeign and creates Bean
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${service.security.secure-key-username}") String name,
            @Value("${service.security.secure-key-password}") String password
    )
    {
        return new BasicAuthRequestInterceptor(name, password);
    }
}
