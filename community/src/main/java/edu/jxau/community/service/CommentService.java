package edu.jxau.community.service;

import edu.jxau.community.dao.CommentMapper;
import edu.jxau.community.entity.Comment;
import edu.jxau.community.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @title: community
 * @ClassName CommentService.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Service
public class CommentService implements CommunityConstant {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilterService sensitiveFilterService;

    @Autowired
    private DiscussPostService discussPostService;

    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }

    public int selectCommentCount(int entityType, int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }

    /**
     * 事务控制
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addComment(Comment comment){

        if (comment == null){
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 转义
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        // 过滤敏感词
        comment.setContent(sensitiveFilterService.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        if (comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(ENTITY_TYPE_POST, comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);
        }

        return rows;
    }
}
