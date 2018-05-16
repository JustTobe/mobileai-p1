package com.mobileai.luncert.service;


import com.mobileai.luncert.model.mysql.UserMapper;
import com.mobileai.luncert.model.mysql.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagement {

    @Autowired
    private UserMapper userMapper;

    public User validate(String name, String password) {
        try {
            return userMapper.queryUserByAccountPassword(name, password);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean regis(String account, String password) {
        if (userMapper.queryUserByAccount(account) != null) return false;
       else {
        userMapper.regis(account, password);
        return true;
       }
    }

    public void setActivateIdentifier(String activateIdentifier, String account) {
        userMapper.setActivateIdentifier(activateIdentifier, account);
    }

    public boolean activate(String activateIdentifier, String account) {
        String identifier = userMapper.getActivateIdentifer(account);
        if (activateIdentifier.compareTo(identifier) == 0) {
            userMapper.activate(account);
            return true;            
        } else return false;
    }

}