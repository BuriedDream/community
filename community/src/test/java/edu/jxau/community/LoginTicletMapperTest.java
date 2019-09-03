package edu.jxau.community;

import edu.jxau.community.dao.LoginTicketMapper;
import edu.jxau.community.entity.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @title: community
 * @ClassName LoginTicletMapperTest.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTicletMapperTest {

    @Autowired(required = false)
    LoginTicketMapper loginTicketMapper;

    @Test
    public void add(){
        LoginTicket ticket = new LoginTicket();
        ticket.setStatus(0);
        ticket.setUserId(102);
        ticket.setTicket("abc");
        Date date = new Date();
        date.setTime(date.getTime() + 10*24*60*60*1000);
        ticket.setExpired(date);

        loginTicketMapper.insertLoginTicket(ticket);
    }

    @Test
    public void select(){
        loginTicketMapper.updateStatus("abc",1);
        LoginTicket ticket = loginTicketMapper.selectByTicket("abc");
        System.out.println(ticket);
    }
}
