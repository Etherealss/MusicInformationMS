package pers.wtk.dao;


import org.apache.ibatis.annotations.Param;
import pers.wtk.pojo.po.User;
import pers.wtk.pojo.po.User;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-05-24
 */
public interface UserDao {

    /**
     * 添加新用户
     * @param user
     * @return
     */
    boolean insertUser(User user);

    /**
     * 直接查询用户
     * @param userId
     * @return
     */
    User getUser(long userId);

    /**
     * 不查询密码
     * @param userId
     * @return
     */
    User getUserInfo(long userId);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    boolean deleteUser(Long userId);

    /**
     * 修改用户的普通信息
     * @param user
     * @return
     */
    boolean updateUserInfo(User user);

    /**
     * 修改密码
     * @param userId
     * @param oldPw
     * @param newPw
     * @return
     */
    boolean updateUserPassword(@Param("userId") long userId, @Param("oldPw") String oldPw,
                               @Param("newPw") String newPw);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean deleteMultiUser(@Param("ids") Long[] ids);


    /**
     * 获取记录条数
     * @return
     */
    int getUserSize();

    /**
     * 获取记录条数
     * @param username
     * @return
     */
    int getUserSizeByUserName(String username);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @return
     */
    List<User> getRangeUser(@Param("start") int start, @Param("offset") int offset);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @param username
     * @return
     */
    List<User> getRangeUserByUsername(@Param("start") int start, @Param("offset") int offset,
                                      @Param("username") String username);
}
