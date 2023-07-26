package com.example.motobikestore;

import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBlazeRepositories
@EnableEntityViews("com.example.motobikestore.view")
@SpringBootApplication
public class MotobikeStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotobikeStoreApplication.class, args);
    }

}
