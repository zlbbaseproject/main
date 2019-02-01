package com.zbin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by zhenglibin on 2018/8/22.
 */
@Mapper
public interface DemoDao {
    @Select("select * from t_user where user_id=#{idstr}")
    List<Map<String,String>> getUserInfo(String idstr);
}
