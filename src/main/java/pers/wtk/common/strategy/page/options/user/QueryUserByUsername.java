package pers.wtk.common.strategy.page.options.user;

import pers.wtk.common.strategy.page.PageStrategy4User;
import pers.wtk.dao.UserDao;
import pers.wtk.pojo.po.User;

import java.util.List;

/**
 * @author wtk
 * @description 通过用户名获取
 * @date 2021-06-16
 */
public class QueryUserByUsername extends PageStrategy4User {

    public QueryUserByUsername(UserDao userDao) {
        super(userDao);
    }

    /**
     * 获取记录数
     * @param userName
     * @return
     */
    @Override
    public int getTotalCount(String userName) {
        return userDao.getUserSizeByUserName(userName);
    }

    /**
     * 查询多条用户记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param username
     * @return
     */
    @Override
    public List<User> getPage(int start, int offset, String username) {
        return userDao.getRangeUserByUsername(start, offset, username);
    }
}
