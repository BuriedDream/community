package edu.jxau.community.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: community
 * @ClassName CookieUtil.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name){

        if(request == null || name == null){
            throw new IllegalArgumentException("参数不能为空！");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
