package edu.jxau.community.dao;

import edu.jxau.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: community
 * @ClassName MessageMapper.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的私信列表，返回每个会话的最新消息
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectConversations(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    /**
     * 查询指定用户的会话数量
     * @param userId
     * @return
     */
    int selectConversationCount(int userId);

    /**
     * 查询指定会话的私信列表
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectLetters(@Param("conversationId") String conversationId,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    /**
     * 查询会话的消息数量
     * @param conversationId
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询指定用户未读数量
     * @param userId
     * @param conversationId
     * @return
     */
    int selectLetterUnreadCount(@Param("userId") int userId,
                                @Param("conversationId") String conversationId);

    /**
     * 新增信息
     * @param message
     * @return
     */
    int insertMassage(Message message);

    /**
     * 修改消息状态
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);
}
