package com.mobileai.luncert.model.mysql;


import com.mobileai.luncert.Application;
import com.mobileai.luncert.model.mysql.entity.User;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserMapper {

    private static Logger logger = Logger.getLogger(TestUserMapper.class);
    
    @Autowired
    UserMapper userMapper;

    // @Test
    public void testQueryByPassword() {
        User user = userMapper.queryUserByAccountPassword("admin", "123");
        logger.info(user.getId() + " : " + user.getAccount());
    }

    // @Test
    public void testRegis() {
        userMapper.regis("xxx", "123");
    }

    // @Test
    public void testSetActivateIdentifier() {
        userMapper.setActivateIdentifier("xxxx", "admin");
    }

}