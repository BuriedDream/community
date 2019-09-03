package edu.jxau.community.utils;

import edu.jxau.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @title: community
 * @ClassName HostHolder.java
 * @Description: 持有用户信息，代替 session
 * @Author: liam
 * @Version:
 **/
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
