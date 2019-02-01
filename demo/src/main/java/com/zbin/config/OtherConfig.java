package com.zbin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhenglibin on 2018/12/3.
 */
@Configuration
public class OtherConfig  implements CommandLineRunner {
    @Value("${other}")
    private Boolean other = null;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(this.other);
    }
}
