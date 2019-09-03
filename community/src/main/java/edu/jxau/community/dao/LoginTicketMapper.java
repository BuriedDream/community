package edu.jxau.community.dao;

import edu.jxau.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @title: community
 * @ClassName LoginTicketMapper.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Mapper
public interface LoginTicketMapper {

    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, ticket, status, expired ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    /**
     * 添加 ticket,自动生成主键 id
     * @param loginTicket
     * @return 插入了条数
     */
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values(#{userId},#{ticket},#{status},#{expired})"})
    @Options(keyProperty = "id",useGeneratedKeys = true)
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 根据 ticket 查找指定的 LoginTicket
     * @param ticket
     * @return LoginTicket
     */
    @Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where ticket = #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * 根据ticket 修改指定 LoginTicket 的状态
     * @param ticket
     * @param status
     * @return 修改的记录数
     */
    @Update({"update",TABLE_NAME," set status = #{status} where ticket = #{ticket}"})
    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
