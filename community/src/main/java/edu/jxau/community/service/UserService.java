package edu.jxau.community.service;

import edu.jxau.community.dao.LoginTicketMapper;
import edu.jxau.community.dao.UserMapper;
import edu.jxau.community.entity.LoginTicket;
import edu.jxau.community.entity.User;
import edu.jxau.community.utils.CommunityConstant;
import edu.jxau.community.utils.CommunityUtil;
import edu.jxau.community.utils.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @title: community
 * @ClassName UserService.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Service
public class UserService implements CommunityConstant {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired(required = false)
    private LoginTicketMapper loginTicketMapper;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public User findUserByName(String name){
        return userMapper.selectByName(name);
    }

    public Map<String,Object> register(User user){
        Map<String, Object> map = new HashMap<>();

        if (user == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        else if (StringUtils.isBlank(user.getUsername())){
            map.put("msg","用户名不能为空");
            return map;
        }
        else if (StringUtils.isBlank(user.getPassword())){
            map.put("msg","密码不能为空");
            return map;
        }
        else if (StringUtils.isBlank(user.getEmail())){
            map.put("msg","邮箱不能为空");
            return map;
        }

        User u = userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("msg","用户名已被注册");
            return map;
        }
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("msg","邮箱已被注册");
            return map;
        }
        u = null;

        //注册用户
        user.setType(0);
        user.setStatus(0);
        user.setCreateTime(new Date());
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        userMapper.insertUser(user);

        // 激活邮箱（后期试着把这个操作异步化）
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);

        return map;
    }

    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    public Map<String,Object> login(String username, String password, int expiredSeconds){
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","用户名不能为空！");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空！");
            return map;
        }

        User user = userMapper.selectByName(username);
        if (user == null){
            map.put("usernameMsg","该用户未注册！");
            return map;
        }
        if(user.getStatus() == 0){
            map.put("usernameMsg","该账号未激活！");
            return map;
        }

        password = CommunityUtil.md5(password + user.getSalt());
        if (!password.equals(user.getPassword())){
            map.put("passwordMsg","密码错误！");
            return map;
        }

        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(user.getId());
        ticket.setTicket(CommunityUtil.generateUUID());
        ticket.setStatus(0);
        ticket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds*1000));
        loginTicketMapper.insertLoginTicket(ticket);

        map.put("ticket", ticket.getTicket());
        return map;
    }

    public void logout(String ticket){
        /** 修改状态时会修改过期时间为当前时间
         * 因为expired为timestamp类型，修改记录时会把timestamp字段更新为当前时间
         */
        loginTicketMapper.updateStatus(ticket,1);
    }

    public LoginTicket findLoginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }

    public int updateHeader(int userId, String headerUrl){
        return userMapper.updateHead(userId,headerUrl);
    }

    public int updatePassword(int userId, String password){
        return userMapper.updatePassword(userId, password);
    }
}
