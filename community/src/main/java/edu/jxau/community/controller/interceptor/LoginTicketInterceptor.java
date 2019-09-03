package edu.jxau.community.controller.interceptor;

import edu.jxau.community.entity.LoginTicket;
import edu.jxau.community.entity.User;
import edu.jxau.community.service.UserService;
import edu.jxau.community.utils.CookieUtil;
import edu.jxau.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @title: community
 * @ClassName LoginTicketInterceptor.java
 * @Description: ticket 拦截器
 * @Author: liam
 * @Version:
 **/
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 从 cookie 中得到ticket
        String ticket = CookieUtil.getValue(request,"ticket");
        if (ticket != null){
            LoginTicket loginTicket = userService.findLoginTicket(ticket);

            if(loginTicket!=null && loginTicket.getStatus()==0 && loginTicket.getExpired().after(new Date())){
                hostHolder.setUser(userService.findById(loginTicket.getUserId()));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        hostHolder.clear();
    }
}
