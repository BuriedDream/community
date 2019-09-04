package edu.jxau.community;

import edu.jxau.community.service.SensitiveFilterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: community
 * @ClassName SensitiveFilterTest.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SensitiveFilterTest {

    @Autowired
    SensitiveFilterService sensitiveFilterService;

    @Test
    public void test(){
        String text = "少你 阿 宾，答案包 收氨基酸佛i吉萨" +
                "答案提供 拉萨覅" +
                "打标语" +
                "打错门 萨法" +
                "打 飞 机专" +
                "打死经过 暗室逢灯" +
                "打死人" +
                "打砸办公 萨法";

        System.out.println(sensitiveFilterService.filter(text));
    }
}
