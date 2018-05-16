package com.mobileai.luncert.service;

import com.mobileai.luncert.entity.User;
import com.mobileai.luncert.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User validate(String name, String password) {
        try {
            return userMapper.queryByPassword(name, password);
        } catch (Exception e) {
            return null;
        }
    }

}