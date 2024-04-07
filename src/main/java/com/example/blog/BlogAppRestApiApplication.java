package com.example.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.*;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST APIs",
                description = "Spring boot Blog App REST APIs",
                version = "v1.0",
                contact = @Contact(
                        name = "Tomas Vrbada",
                        email = "nhatphay7@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        )
)
public class BlogAppRestApiApplication {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setSkipNullEnabled(true);
        return modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogAppRestApiApplication.class, args);
    }

}
