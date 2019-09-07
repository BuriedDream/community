package edu.jxau.community.controller;

import edu.jxau.community.entity.User;
import edu.jxau.community.service.LikeService;
import edu.jxau.community.utils.CommunityUtil;
import edu.jxau.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: community
 * @ClassName LikeController.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "like", method = RequestMethod.GET)
    @ResponseBody
    public String kile(int entityType, int entityId, int entityUserId){
        User user = hostHolder.getUser();

        //点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        //点赞数量
        int likeCount = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);
        //状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);

        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return CommunityUtil.getJSONString(0, null, map);

    }
}
