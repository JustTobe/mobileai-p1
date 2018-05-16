package com.mobileai.luncert.mapper;


import com.mobileai.luncert.Application;
import com.mobileai.luncert.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserMapper {
    
    @Autowired
    UserMapper userMapper;

    @Test
    public void testQueryByPassword() {
        User user = userMapper.queryByPassword("amind", "123");
        System.out.println(user.getId() + " : " + user.getName());
    }

}