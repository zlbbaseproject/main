package com.zbin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zhenglibin on 2018/4/16.
 */
@Configuration
@PropertySource(value = "classpath:dev.properties")
public class DevConfig  implements CommandLineRunner {


    @Value("${usernames}")
    public String username;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(this.username);
    }
}
