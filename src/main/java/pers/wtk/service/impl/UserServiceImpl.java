package pers.wtk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.BadRequestException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.strategy.page.PageChoose;
import pers.wtk.common.strategy.page.mapper.DaoMapper;
import pers.wtk.common.strategy.page.mapper.UserDaoMapper;
import pers.wtk.dao.AdminDao;
import pers.wtk.dao.UserDao;
import pers.wtk.pojo.po.User;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.UserService;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger("root");
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;

    @Override
    public User register(User user) throws ActionFailException {
        if (userDao.getUser(user.getId()) != null) {
            throw new ActionFailException("用户id已存在");
        }
        // 注册用户，插入id属性
        userDao.insertUser(user);

        return user;
    }

    @Override
    public User login(long userId, String password, boolean isAdminLogin) throws BadRequestException {
        // 查询用户并判断是否存在该用户，不存在则报异常
        User user = userDao.getUser(userId);
        if (user == null) {
            logger.debug("用户不存在");
            throw new NotFoundException("用户不存在");
        }
        // 检查密码是否匹配
        logger.debug("登录用户：{}", user);
        if (!password.equals(user.getPassword())) {
            // 密码不匹配
            logger.debug("密码不匹配");
            throw new ActionFailException("密码错误");
        }

        if (isAdminLogin) {
            // 管理员登录
            List<String> userPremissions = adminDao.getUserPremissions(userId);

            if (userPremissions.size() == 0) {
                throw new ActionFailException("不是管理员账号！");
            }

            user.setPermissions(userPremissions);
        }

        return user;
    }


    @Override
    public User getUserInfo(long userId) throws NotFoundException {
        User user = userDao.getUserInfo(userId);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        List<String> userPremissions = adminDao.getUserPremissions(user.getId());
        user.setPermissions(userPremissions);
        return user;
    }

    @Override
    public void updateUserInfo(User user) throws NotFoundException {
        boolean found = userDao.updateUserInfo(user);
        if (!found) {
            throw new NotFoundException("用户不存在");
        }
    }

    @Override
    public void updateUserPassword(long userId, String oldPw, String newPw) throws ActionFailException {
        boolean success = userDao.updateUserPassword(userId, oldPw, newPw);
        if (!success) {
            throw new ActionFailException("修改密码失败，可能是账号不存在，或者是原密码不整齐");
        }
    }

    @Override
    public void cancellateUser(long userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public void cancellateMultiUser(Long[] ids) throws Exception {
        userDao.deleteMultiUser(ids);
    }

    @Override
    public Page<User> getPageUser(int curPage, int offset, Long userId, String username) throws Exception {
        logger.trace("分页搜索：curPage:{}, offset:{}, userId:{}, username:{}",
                curPage, offset, userId, username);

        if (curPage < 0) {
            throw new ActionFailException("当前页码小于1，不合法");
        }

        /*
         策略模式选择
         */
        DaoMapper<User> daoMapper = new UserDaoMapper(curPage, offset, userDao);
        PageChoose<User> pageChoose = PageChoose.buildPageChoose(userId, username, daoMapper);
        // 按歌手分页
        Page<User> page = pageChoose.getPage();
        List<User> userList = page.getDataList();
        for (User user : userList) {
            List<String> userPremissions = adminDao.getUserPremissions(user.getId());
            user.setPermissions(userPremissions);
        }
        return page;
    }
}
