package edu.jxau.community;

import edu.jxau.community.dao.UserMapper;
import edu.jxau.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: community
 * @ClassName userMapperTest.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class userMapperTest {

    @Autowired(required = false)
    UserMapper userMapper;

    @Value("community.path.upload")
    private String uploadPath;

    @Test
    public void testSelectByName(){
        User user = userMapper.selectByName("liam");
        System.out.println(user);
    }

    @Test
    public void testPath(){
        System.out.println(uploadPath);
    }
}
