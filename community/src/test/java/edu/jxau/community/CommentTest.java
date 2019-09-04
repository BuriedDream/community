package edu.jxau.community;

import edu.jxau.community.entity.Comment;
import edu.jxau.community.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @title: community
 * @ClassName CommentTest.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    @Autowired
    CommentService commentService;

    @Test
    public void test(){
        Comment comment = new Comment();
        comment.setEntityType(1);
        comment.setContent("少年阿宾，有个大鸡巴！");
        comment.setTargetId(111);
        comment.setEntityId(145);
        comment.setCreateTime(new Date());

        commentService.addComment(comment);

        List<Comment> list = commentService.findCommentsByEntity(1,111,0,5);
        int count = commentService.selectCommentCount(1,111);

        System.out.println(count);
    }
}
