package com.tbond.yumdash;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class YumDashApplication
//implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(YumDashApplication.class, args);
        System.out.println("main is finished!");
    }

//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Hello World! It's YumDash project!");
//    }

    @Bean
    @Order(2)
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Hello World");
        };
    }
}


