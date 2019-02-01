package com.zbin.controller;

import com.zbin.config.propertiesGetConfig;
import com.zbin.model.User;
import com.zbin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private propertiesGetConfig propertiesgetConfig;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(){
        return userService.findAllUser();
    }

    @RequestMapping("/w")
    public String world(ModelMap map) {

        map.addAttribute("host", "http://www.shanshan.com");
        return "world";
    }

    @RequestMapping("/w2")
    public String world2(ModelMap map) {
        map.addAttribute("testStr", "你好");
        return "test";
    }

    @RequestMapping("/w3")
    public String world3(String test,ModelMap map) {
        String teststr = Optional.ofNullable(test).orElse("空").toString();
        redisTemplate.opsForValue().set("test",teststr);
        map.addAttribute("testStr",teststr);
        return "test3";
    }
    @RequestMapping("/w4")
    public String world4(ModelMap map) {
        String test = Optional.ofNullable(redisTemplate.opsForValue().get("test")).orElse("").toString();
        map.addAttribute("testStr",userService.findAllUser());
        return "test3";
    }
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(propertiesgetConfig.getServer());

    }

    @ResponseBody
    @RequestMapping(value = "/allDemo", produces = {"application/json;charset=UTF-8"})
    public Object findDEMOUser(){
        return userService.findDEMOUser("1000");
    }
}