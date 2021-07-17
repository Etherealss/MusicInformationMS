package pers.wtk.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.wtk.pojo.po.User;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/spring-config.xml"})
public class UserDaoTest {

    private Logger logger = LoggerFactory.getLogger("testLogger");
    @Qualifier("userDao")
    @Autowired
    private UserDao dao;

    @Test
    public void testInsertUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setPassword("test3");
        user.setUsername("test3");
        user.setSex(true);
        user.setBalance(2.0F);
        user.setRegisterDate(new Date());
        dao.insertUser(user);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = dao.getUser(3L);
        logger.debug(user.toString());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setPassword("test3");
        user.setUsername("test3");
        user.setSex(true);
        user.setBalance(2.0F);
        user.setRegisterDate(new Date());
        boolean b = dao.updateUserInfo(user);
        System.out.println(b);
    }

    @Test
    public void testDeleteUser() throws Exception {
        dao.deleteUser(3L);
    }

    @Test
    public void testFindAllUser() throws Exception {
        List<User> allUser = dao.getAllUser();
        for (User user : allUser) {
            logger.debug(user.toString());
        }
    }
}