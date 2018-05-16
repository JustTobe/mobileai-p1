package com.mobileai.luncert.model.mysql;


import com.mobileai.luncert.model.mysql.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from User where account = #{account}")
    @Result(property = "id", column = "id", javaType = int.class)
    public int queryIdByAccount(@Param("account") String account);


    @Select("select * from User where account = #{account}")
    @Results({
        @Result(property = "id", column = "id", javaType = int.class),
        @Result(property = "account", column = "account", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class),
        @Result(property = "isActive", column = "isActive", javaType = Boolean.class),
        @Result(property = "isAdmin", column = "isActive", javaType = Boolean.class)
    })
    public User queryUserByAccount(@Param("account") String account);

    @Select("select * from User where account = #{account} and password = #{password}")
    @Results({
        @Result(property = "id", column = "id", javaType = int.class),
        @Result(property = "account", column = "account", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class),
        @Result(property = "isActive", column = "isActive", javaType = Boolean.class),
        @Result(property = "isAdmin", column = "isActive", javaType = Boolean.class)
    })
    public User queryUserByAccountPassword(@Param("account")String account,@Param("password") String password);

    @Insert("insert into User(account, password) values(#{account}, #{password})")
    public void regis(@Param("account") String account, @Param("password") String password);


    // isActive

    @Select("select isActive from User where account = #{account}")
    public boolean isActive(@Param("account") String account);

    @Update("update User set isActive = true where account = #{account}")
    public void activate(@Param("account") String account);

    // isAdmin

    @Select("select isAdmin from User where account = #{account}")
    public boolean isAdmin(@Param("account") String account);

    // activateIdentifier

    @Update("update User set activateIdentifier = #{activateIdentifier} where account = #{account}")
    public void setActivateIdentifier(@Param("activateIdentifier") String activateIdentifier, @Param("account") String account);

    @Select("select activateIdentifier from User where account = #{account}")
    public String getActivateIdentifer(@Param("account") String account);
    
}