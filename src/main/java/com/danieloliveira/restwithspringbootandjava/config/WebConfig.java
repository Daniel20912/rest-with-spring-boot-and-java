package com.danieloliveira.restwithspringbootandjava.config;

import com.danieloliveira.restwithspringbootandjava.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

    @Value("${cors.originPatterns: default}")
    private String corsOriginPatterns = "";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                // .allowedMethods("GET", "POST", "PUT")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true); // possibilita autenticação
    }

    // permite passar como parametro o tipo de retorno na url
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {


        // VIA QUERY PARAM
        /*
        configurer.favorParameter(true) // aceita parâmetros
                .parameterName("mediaType") // nome do parâmetro
                .ignoreAcceptHeader(true) // ignora parâmetros no header
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) // o mediatype padrão vai ser json
                .mediaType("json", MediaType.APPLICATION_JSON) // tipos de dados que serão suportados
                .mediaType("xml", MediaType.APPLICATION_XML);
         */


        // VIA HEADER PARAM
        configurer.favorParameter(false) // aceita parâmetros
                .ignoreAcceptHeader(false) // permite parâmetros no header
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) // o mediatype padrão vai ser json
                .mediaType("json", MediaType.APPLICATION_JSON) // tipos de dados que serão suportados
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yml", MEDIA_TYPE_APPLICATION_YML);
    }
}
