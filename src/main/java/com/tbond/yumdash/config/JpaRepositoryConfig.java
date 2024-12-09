package com.tbond.yumdash.config;

import com.tbond.yumdash.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.tbond.yumdash.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class
)
public class JpaRepositoryConfig {
}
