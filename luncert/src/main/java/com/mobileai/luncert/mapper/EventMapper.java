package com.mobileai.luncert.mapper;

import java.util.Date;
import java.util.List;

import com.mobileai.luncert.model.mysql.Event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("select * from Event where alert = true order by id desc limit 0, 1")
    public Event fetchLastAlert();

    @Select("select * from Event")
    public List<Event> fetchAll();

    @Select("select * from Event where id > #{id}")
    public List<Event> fetchAllAfter(@Param("id") int id);

    @Select("select * from Event limit 0, 1000")
    public List<Event> fetch1000();

    @Insert("insert into Event(time, source, target, eventId, alert) values(#{time}, #{source}, #{target}, #{eventId}, #{alert})")
    public void addEvent(@Param("time") Date time, @Param("source") int source, @Param("target") int target, @Param("eventId") int eventId, @Param("alert") boolean alert);

}