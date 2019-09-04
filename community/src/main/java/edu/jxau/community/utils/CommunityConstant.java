package edu.jxau.community.utils;

/**
 * @title: community
 * @ClassName CommunityConstant.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
public interface CommunityConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认过期时间
     */
    int DEFAULT_EXPIRED_SECONDS = 12*3600;

    /**
     * 记住密码的过期时间
     */
    int REMEMBER_EXPIRED_SECONDS = 15*24*3600;

    /**
     *  实体类型：贴子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     *  实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;
}
