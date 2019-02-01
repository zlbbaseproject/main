package com.zbin.config;

/**
 * Created by zhenglibin on 2018/4/16.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix ="my.myserver")
public class propertiesGetConfig {
    private List<String> server = new ArrayList<String>();

    public List<String> getServer(){
        return this.server;
    }
}
