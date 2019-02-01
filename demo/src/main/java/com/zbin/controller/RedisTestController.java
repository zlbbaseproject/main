package com.zbin.controller;

import com.zbin.constants.RedisKey;
import com.zbin.model.User;
import com.zbin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhenglibin on 2018/8/3.
 */
@Controller
@RequestMapping(value = "/redisTest")
public class RedisTestController {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping("/firstWrite")
    public String firstWrite(ModelMap map) {
        RedisUtil.writeRedisObject(redisTemplate, RedisKey.MY_FIRST_TEST,new User("zlb"));
        map.addAttribute("testStr", "你好");
        return "test";
    }

    @RequestMapping("/firstRead")
    public String firstRead(ModelMap map) {
        User u = RedisUtil.readRedisObject(redisTemplate,RedisKey.MY_FIRST_TEST,User.class);
        map.addAttribute("testStr", u.getUserName());
        return "test";
    }
}
