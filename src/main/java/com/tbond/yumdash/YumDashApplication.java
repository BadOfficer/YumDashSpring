package com.tbond.yumdash;

import com.tbond.yumdash.test.BeansTester;
import com.tbond.yumdash.test.ShowBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class YumDashApplication {

    public static void main(String[] args) {

        SpringApplication.run(YumDashApplication.class, args);
    }

    @Bean
    @Scope("prototype")
    ShowBean showBean() {
        return new ShowBean();
    }

    BeansTester beansTester() {
        return new BeansTester(showBean(), showBean());
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            beansTester().compareBeans();
        };
    }
}


