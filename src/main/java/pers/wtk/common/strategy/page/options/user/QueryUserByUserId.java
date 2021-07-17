package pers.wtk.common.strategy.page.options.user;

import pers.wtk.common.strategy.page.PageStrategy4Singer;
import pers.wtk.common.strategy.page.PageStrategy4User;
import pers.wtk.dao.SingerDao;
import pers.wtk.dao.UserDao;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @description 通过用户ID获取
 * @date 2021-06-16
 */
public class QueryUserByUserId extends PageStrategy4User {
    public QueryUserByUserId(UserDao userDao) {
        super(userDao);
    }

    @Override
    public int getTotalCount(String singerId) {
        return 1;
    }

    @Override
    public List<User> getPage(int start, int offset, String singerIdStr) {
        List<User> list = new ArrayList<>(1);
        list.add(userDao.getUser(Long.parseLong(singerIdStr)));
        return list;
    }
}
