package pers.wtk.service;

import pers.wtk.pojo.po.User;
import pers.wtk.pojo.vo.Page;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-08
 */
public interface UserService {

    /**
     * 注册
     * @param user
     * @return
     * @throws Exception
     */
    User register(User user) throws Exception;

    /**
     * 登录
     * @param userId
     * @param password
     * @param isAdminLogin
     * @return
     * @throws Exception
     */
    User login(long userId, String password, boolean isAdminLogin) throws Exception;


    /**
     * 获取已登录的用户的信息
     * @param userId
     * @return
     * @throws Exception
     */
    User getUserInfo(long userId) throws Exception;

    /**
     * 修改用户信息
     * @param user
     * @throws Exception
     */
    void updateUserInfo(User user) throws Exception;

    /**
     * 修改密码
     * @param userId
     * @param originalPw
     * @param newPw
     * @throws Exception
     */
    void updateUserPassword(long userId, String originalPw, String newPw) throws Exception;

    /**
     * 注销用户
     * @param userId
     * @throws Exception
     */
    void cancellateUser(long userId) throws Exception;

    /**
     * 注销多个用户
     * @param ids
     * @throws Exception
     */
    void cancellateMultiUser(Long[] ids) throws Exception;


    /**
     * 分页查询用户
     * @param curPage
     * @param offset
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    Page<User> getPageUser(int curPage, int offset, Long userId, String userName) throws Exception;
}
