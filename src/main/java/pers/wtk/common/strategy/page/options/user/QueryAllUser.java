package pers.wtk.common.strategy.page.options.user;

import pers.wtk.common.strategy.page.PageStrategy4User;
import pers.wtk.dao.UserDao;
import pers.wtk.pojo.po.User;

import java.util.List;

/**
 * @author wtk
 * @description 获取分页用户，没有限制
 * @date 2021-06-16
 */
public class QueryAllUser extends PageStrategy4User {
    public QueryAllUser(UserDao userDao) {
        super(userDao);
    }

    /**
     * 获取记录数
     * @return
     */
    @Override
    public int getTotalCount(String keyword) {
        return userDao.getUserSize();
    }

    /**
     * 查询多条用户记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword 为空
     * @return
     */
    @Override
    public List<User> getPage(int start, int offset, String keyword) {
        return userDao.getRangeUser(start, offset);
    }
}
