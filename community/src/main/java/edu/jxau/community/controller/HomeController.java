package edu.jxau.community.controller;

import edu.jxau.community.entity.DiscussPost;
import edu.jxau.community.entity.Page;
import edu.jxau.community.entity.User;
import edu.jxau.community.service.DiscussPostService;
import edu.jxau.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * @title: community
 * @ClassName HomeController.java
 * @Description: 首页控制器
 * @Author: liam
 * @Version:
 **/
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public String getIndex(Model model, Page page){

        page.setRows(discussPostService.findDiscussPostsRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(list != null){
            for (DiscussPost discussPost : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user",user);

                discussPosts.add(map);
            }
        }

        model.addAttribute("discussPosts",discussPosts);
        return "index";
    }
}
