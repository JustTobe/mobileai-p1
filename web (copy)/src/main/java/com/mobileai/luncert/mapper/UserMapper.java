package com.mobileai.luncert.mapper;

import com.mobileai.luncert.entity.User;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from User where name = #{param1} and password = #{param2}")
    @Results({
        @Result(property = "id", column = "id", javaType = int.class),
        @Result(property = "name", column = "name", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class)
    })
    public User queryByPassword(String name, String password);
    
}