package edu.jxau.community.service;

import edu.jxau.community.dao.MessageMapper;
import edu.jxau.community.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @title: community
 * @ClassName MessageService.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Service
public class MessageService {

    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilterService sensitiveFilterService;

    public List<Message> findConversations(int userId, int offset, int limit){
        return messageMapper.selectConversations(userId,offset,limit);
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit){
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId){
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }

    public int insertMessage(Message message){

        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilterService.filter(message.getContent()));
        return messageMapper.insertMassage(message);
    }

    public int readMessage(List<Integer> ids){
        return messageMapper.updateStatus(ids, 1);
    }
}
