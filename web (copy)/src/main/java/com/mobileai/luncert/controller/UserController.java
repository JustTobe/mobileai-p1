package com.mobileai.luncert.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.entity.User;
import com.mobileai.luncert.service.UserService;
import com.mobileai.luncert.utils.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private String genPass(String name, String password) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(password).append(new Date());
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] input = builder.toString().getBytes();
        md.update(input);
        byte[] md5Bytes = md.digest();
        BigInteger bigInteger = new BigInteger(1, md5Bytes);
        return bigInteger.toString(16);
    }

    /**
     * return User if validate successfully
     */
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public String validate(HttpServletResponse rep, String name, String password) throws Exception {
        User user = userService.validate(name, password);
        if (user != null) {
            rep.addCookie(new Cookie("usrpass", genPass(name, password)));
            return ResponseWrapper.response(200, null, JSONObject.fromObject(user));
        } else return ResponseWrapper.response(201, "validate failed", null);
    }

}