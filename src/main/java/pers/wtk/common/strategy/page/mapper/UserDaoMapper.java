package pers.wtk.common.strategy.page.mapper;

import pers.wtk.dao.UserDao;
import pers.wtk.pojo.po.User;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-07-18
 */
public class UserDaoMapper extends DaoMapper<User> {

    private UserDao userDao;

    public UserDaoMapper(int curPage, int offset, UserDao userDao) {
        super(curPage, offset);
        this.userDao = userDao;
    }

    public UserDaoMapper(int curPage, int offset, String keyword, UserDao userDao) {
        super(curPage, offset, keyword);
        this.userDao = userDao;
    }

    @Override
    public List<User> queryAll() {
        return userDao.getRangeUser(start, offset);
    }

    @Override
    public int queryAllSize() {
        return userDao.getUserSize();
    }

    @Override
    public User queryById() {
        return userDao.getUser(Long.parseLong(keyword));
    }

    @Override
    public List<User> queryByName() {
        return userDao.getRangeUserByUsername(start, offset, keyword);
    }

    @Override
    public int queryByNameSize() {
        return userDao.getUserSizeByUserName(keyword);
    }
}
