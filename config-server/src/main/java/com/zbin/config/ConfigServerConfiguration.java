package com.zbin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.JGitEnvironmentProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by TDW on 2018/12/6.
 */
@Configuration
public class ConfigServerConfiguration {
    @Autowired
    private ConfigurableEnvironment environment;
    @Autowired
    private ConfigServerProperties server;


    @Bean
    @ConfigurationProperties("config.server.resource")
    public ZJGitEnvironmentRepository resourceEnvironmentRepository() {
        ZJGitEnvironmentRepository repository = new ZJGitEnvironmentRepository(this.environment,new JGitEnvironmentProperties());
        if (this.server.getDefaultLabel()!=null) {
            repository.setDefaultLabel(this.server.getDefaultLabel());
        }
        return repository;
    }

    @Bean
    @ConfigurationProperties("config.server.config")
    public ZJGitEnvironmentRepository defaultEnvironmentRepository() {
        ZJGitEnvironmentRepository repository = new ZJGitEnvironmentRepository(this.environment,new JGitEnvironmentProperties());
        if (this.server.getDefaultLabel()!=null) {
            repository.setDefaultLabel(this.server.getDefaultLabel());
        }
        return repository;
    }

    @Bean
    @ConfigurationProperties("config.server.base")
    public ZJGitEnvironmentRepository baseRepo() {
         JGitEnvironmentProperties pro = new JGitEnvironmentProperties();
        pro.setSearchPaths("config-pro");
        ZJGitEnvironmentRepository repository = new ZJGitEnvironmentRepository(this.environment,pro);
        if (this.server.getDefaultLabel()!=null) {
            repository.setDefaultLabel(this.server.getDefaultLabel());
        }
        return repository;
    }


}
