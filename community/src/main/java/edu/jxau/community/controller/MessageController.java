package edu.jxau.community.controller;

import edu.jxau.community.entity.Message;
import edu.jxau.community.entity.Page;
import edu.jxau.community.entity.User;
import edu.jxau.community.service.MessageService;
import edu.jxau.community.service.UserService;
import edu.jxau.community.utils.CommunityUtil;
import edu.jxau.community.utils.HostHolder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * @title: community
 * @ClassName MessageController.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Controller
@RequestMapping(path = "/letter")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){

        User user = hostHolder.getUser();

        //分页信息
        page.setLimit(7);
        page.setPath("/letter/list");
        page.setRows(messageService.findConversationCount(user.getId()));

        //会话列表
        List<Message> conversationList =
                messageService.findConversations(user.getId(),page.getOffset(),page.getLimit());
        List<Map<String,Object>> conversations = new ArrayList<>();
        if(conversationList != null){
            for (Message message : conversationList) {
                Map<String,Object> map = new HashMap<>();
                map.put("conversation",message);
                map.put("letterCount",messageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount",messageService.findLetterUnreadCount(user.getId(),message.getConversationId()));

                //参与会话的用户
                int targetId = user.getId() == message.getFromId() ? message.getFromId() : message.getToId();
                map.put("target",userService.findUserById(targetId));

                conversations.add(map);
            }
        }
        model.addAttribute("conversations",conversations);

        int totalLetterUnreadCount = messageService.findLetterUnreadCount(user.getId(),null);
        model.addAttribute("letterUnreadCount",totalLetterUnreadCount);

        return "/site/letter";
    }

    @RequestMapping(path = "/detail/{conversationId}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId,
                                  Model model, Page page){

        //分页信息
        page.setLimit(7);
        page.setPath("/letter/list");
        page.setRows(messageService.findLetterCount(conversationId));

        //会话列表
        List<Message> letterList =
                messageService.findLetters(conversationId,page.getOffset(),page.getLimit());
        List<Map<String,Object>> letters = new ArrayList<>();
        if(letterList != null){
            for (Message message : letterList) {
                Map<String,Object> map = new HashMap<>();
                map.put("letter",message);

                //参与会话的用户
                map.put("fromUser",userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        model.addAttribute("letters",letters);

        // 私信目标
        model.addAttribute("target",getLetterTarget(conversationId));
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()){
            messageService.readMessage(ids);
        }

        return "/site/letter-detail";
    }

    private User getLetterTarget(String conversationId){
        String[] id = conversationId.split("_");
        int id_0 = Integer.parseInt(id[0]);
        int id_1 = Integer.parseInt(id[1]);

        if (hostHolder.getUser().getId() == id_0){
            return userService.findUserById(id_1);
        }else {
            return userService.findUserById(id_0);
        }
    }

    private List<Integer> getLetterIds(List<Message> letterList){
        List<Integer> ids = new ArrayList<>();

        if (letterList != null){
            for(Message message : letterList){

                if (message.getToId() == hostHolder.getUser().getId() && message.getStatus() == 0){
                    ids.add(message.getId());
                }
            }
        }
        return ids;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName, String content){

        User target = userService.findUserByName(toName);
        if (target == null){
            return CommunityUtil.getJSONString(1,"目标用户不存在！");
        }

        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());
        message.setContent(content);
        if (message.getToId() <= message.getFromId()){
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }else {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        }
        message.setCreateTime(new Date());
        messageService.insertMessage(message);

        return CommunityUtil.getJSONString(0);
    }
}
