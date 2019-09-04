package edu.jxau.community.dao;

import edu.jxau.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: community
 * @ClassName CommentMapper.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Mapper
public interface CommentMapper {

    /**
     *  根据指定的实体查找评论
     * @param entityType 实体的类型
     * @param entityId  指定实体
     * @param offset 当前页
     * @param limit 每页条数
     * @return List
     */
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType,
                                         @Param("entityId") int entityId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    /**
     *  查找指定实体的评论数
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(@Param("entityType") int entityType,
                            @Param("entityId") int entityId);


    /**
     * 插入 评论
     * @param comment
     * @return
     */
    int insertComment(Comment comment);
}
