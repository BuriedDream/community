package edu.jxau.community;

import edu.jxau.community.entity.DiscussPost;
import edu.jxau.community.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @title: community
 * @ClassName DiscussPostServiceTest.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscussPostServiceTest {

    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void add(){

        String title = "hello";
        String content = "word";
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setUserId(111);
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);
    }
}
