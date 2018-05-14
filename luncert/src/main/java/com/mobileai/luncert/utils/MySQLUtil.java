package com.mobileai.luncert.utils;

import java.io.Reader;

import com.mobileai.luncert.mapper.EventMapper;
import org.apache.ibatis.session.SqlSession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySQLUtil {

    private SqlSessionFactory sqlSessionFactory;

    public static SqlSession open() { return MySQLUtilInner.INSTANCE.sqlSessionFactory.openSession(); }

    public static void close(SqlSession session) {
        session.commit();
        session.close();
    }

    private MySQLUtil() {
        Reader reader = null;  
        try {
            reader = Resources.getResourceAsReader("mybatis-conf.xml");  
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        sqlSessionFactory.getConfiguration().addMapper(EventMapper.class);
         
    }

    private static class MySQLUtilInner {
        private static final MySQLUtil INSTANCE = new MySQLUtil();
    }

}