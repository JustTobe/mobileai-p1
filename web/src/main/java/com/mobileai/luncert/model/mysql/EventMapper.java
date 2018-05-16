package com.mobileai.luncert.model.mysql;

import java.util.List;

import com.mobileai.luncert.model.mysql.entity.Event;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("select * from Event")
    public List<Event> fetchAll();

    @Select("select * from Event limit 0, #{size}")
    public List<Event> fetchN(@Param("size") int size);
    
}