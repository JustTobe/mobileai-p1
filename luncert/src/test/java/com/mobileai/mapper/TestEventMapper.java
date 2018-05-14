package com.mobileai.mapper;

import java.util.Date;
import java.util.List;

import com.mobileai.luncert.mapper.EventMapper;
import com.mobileai.luncert.model.mysql.Event;
import com.mobileai.luncert.utils.IPUtil;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestEventMapper {

    @Test
    public void testFetchLastAlert() {
        System.out.println("testFetchLastAlert");

        SqlSession session = MySQLUtil.open();

        Event event = session.getMapper(EventMapper.class).fetchLastAlert();
        System.out.println(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());

        MySQLUtil.close(session);
    }

    @Test
    public void testFetchAll() {
        System.out.println("testFetchAll");

        SqlSession session = MySQLUtil.open();

        List<Event> result = session.getMapper(EventMapper.class).fetchAll();
        for (Event event : result) {
            System.out.println(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
        }

		MySQLUtil.close(session);
    }

    @Test
    public void testFetch1000() {
        System.out.println("testFetch1000");

        SqlSession session = MySQLUtil.open();

        List<Event> result = session.getMapper(EventMapper.class).fetchAll();
        for (Event event : result) {
            System.out.println(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
        }

		MySQLUtil.close(session);
    }

    @Test
    public void testAddEvent() {
        System.out.println("testAddEvent");

        SqlSession session = MySQLUtil.open();

        session.getMapper(EventMapper.class).addEvent(new Date(), IPUtil.ipToInt("192.168.3.1"), IPUtil.ipToInt("192.168.240.103"), 1, false);

		MySQLUtil.close(session);
    }

}