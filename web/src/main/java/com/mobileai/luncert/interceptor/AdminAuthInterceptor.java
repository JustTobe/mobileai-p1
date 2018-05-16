package com.mobileai.luncert.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.annotation.AdminAuthCheck;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.sf.json.JSONObject;


@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HandlerMethod methodHandler = (HandlerMethod) handler;
        AdminAuthCheck auth = methodHandler.getMethodAnnotation(AdminAuthCheck.class);
        if (auth != null) {
            if ((boolean)request.getSession().getAttribute("isAdmin")) return true;
            else {
                try (PrintWriter pw = response.getWriter()) {
                    JSONObject json = new JSONObject();
                    json.put("code", 198);
                    json.put("description", "reject access(not administor)");
                    pw.write(json.toString());
                }
            }
        }
        return false;
    }
    
}