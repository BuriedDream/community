package edu.jxau.community.dao;

import edu.jxau.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: community
 * @ClassName DiscussPostMapper.java
 * @Description: DiscussPostMapper
 * @Author: liam
 * @Version:
 **/
@Mapper
public interface DiscussPostMapper {

    /**
     * 分页查找指定用户{userId}帖子
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    /**
     * 查找指定用户的帖子记录总数参数需要用于条件判断且只有一个参数是需要加别名（@Param("")）
     * @param userId
     * @return
     *
     * 当
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 添加帖子
     * @param discussPost
     * @return
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * 修改指定 id 贴子的评论数
     * @param id
     * @param CommentCount
     * @return
     */
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int CommentCount);

    /**
     * 查找指定 id 的帖子
     * @param id
     * @return
     */
    DiscussPost selectDiscussPostById(int id);
}
