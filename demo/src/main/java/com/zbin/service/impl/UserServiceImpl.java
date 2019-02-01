package com.zbin.service.impl;



import com.zbin.dao.DemoDao;
import com.zbin.ds.DS;
import com.zbin.ds.DataSourceNames;
import com.zbin.mapper.UserMapper;
import com.zbin.model.User;
import com.zbin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响
    @Autowired
    private DemoDao demodao;//这里会报错，但是并不会影响

    public int addUser(User user) {

        return userMapper.insertSelective(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
            * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @DS(DataSourceNames.DS2)
    public List<User> findAllUser() {
        return userMapper.selectAllUser();
    }

    @DS(DataSourceNames.MAIN)
    public List<Map<String,String>> findDEMOUser(String idstr) {
        return demodao.getUserInfo(idstr);
    }
}

