package com.mobileai.luncert.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.annotation.AuthCheck;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.sf.json.JSONObject;


@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HandlerMethod methodHandler = (HandlerMethod) handler;
        AuthCheck auth = methodHandler.getMethodAnnotation(AuthCheck.class);
        if (auth != null) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().compareTo("usrpass") == 0 && request.getSession().getAttribute("usrpass").toString().compareTo(cookie.getValue()) == 0) return true;
                }
            }
            try (PrintWriter pw = response.getWriter()) {
                JSONObject json = new JSONObject();
                json.put("code", 199);
                json.put("description", "reject access");
                pw.write(json.toString());
            }
        }
        return false;
    }
    
}