package edu.jxau.community.dao;

import edu.jxau.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @title: community
 * @ClassName UserMapper.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Mapper
public interface UserMapper {

    /**
     * 通过 {id} 查询用户
     * @param id
     * @return
     */
    User selectById(int id);

    /**
     * 通过 {userName} 查询用户
     * @param username
     * @return
     */
    User selectByName(String username);

    /**
     * 通过 {Email} 查找用户
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 插入用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 修改指定 {id}用户的状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") int id,
                     @Param("status") int status);

    /**
     * 修改指定用户的密码
     * @param id
     * @param password
     * @return
     */
    int updatePassword(@Param("id") int id,
                       @Param("password") String password);

    /**
     * 修改用户的头像
     * @param id
     * @param headerUrl
     * @return
     */
    int updateHead(@Param("id") int id,
                   @Param("headerUrl") String headerUrl);
}
