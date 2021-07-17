package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.enums.AppAttribute;
import pers.wtk.common.enums.ResultCode;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.exception.specific.ParametersErrorException;
import pers.wtk.pojo.po.User;
import pers.wtk.pojo.vo.LoginVo;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author wtk
 * @description 用户登录、注册、登出
 * @date 2021-06-09
 */
@SessionAttributes(AppAttribute.LOGGED)
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserSignController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param loginVo
     * @param httpSession 用servletContext判断登录状态，后期使用token
     * @param modelMap 用于共享在线用户的userId
     * @return 200：登录成功
     * 1004：用户已登录
     * 401：密码错误
     * 404：账号不存在
     * @throws ParametersErrorException 参数错误
     * @throws NotFoundException 用户不存在
     * @throws ActionFailException 密码错误
     */
    @PostMapping(value = "/login")
    public Msg login(@RequestBody LoginVo loginVo, ModelMap modelMap, HttpSession httpSession) throws Exception {
        boolean isAdminLogin = loginVo.getIsAdminLogin();
        Long userId = loginVo.getUserId();
        String password = loginVo.getPassword();
        logger.trace("isAdminLogin:{}", isAdminLogin);
        logger.trace("userId：{}, password:{}", userId, password);

        if (userId == null || password == null ||
                password.length() == 0) {
            throw new ParametersErrorException("登录参数异常");
        }
        String userIdStr = String.valueOf(userId);

        logger.debug("用户id：" + userIdStr + ", 用户密码：" + password);
        logger.debug("session中是否有同名id：" + modelMap.get(AppAttribute.LOGGED));

        Msg msg;
        ServletContext servletContext = httpSession.getServletContext();

        // 密码验证通过
        // 用户不存在
        User userLogin = userService.login(userId, password, isAdminLogin);

        //如果servletContext中存在id，说明已登录，比较两个sessionId
        String existUserSessionIdStr = (String) servletContext.getAttribute(userIdStr);
        // 判断已登录和异地登录
        if (existUserSessionIdStr != null) {
            if (existUserSessionIdStr.equals(httpSession.getId())) {
                //如果两个sessionId相同，说明是同个地点登录
                //输出“您已登录”
                logger.info("用户已登录" + userIdStr);
                msg = new Msg(ResultCode.ACTION_NOT_ALLOW, "用户已登录");
            } else {
                // existUserId不为null，但是id与当前session里存的id不匹配，则说明是异地登录，导致sessionId不同
                logger.info("用户异地登录：" + userIdStr);
                msg = Msg.success("异地登录", "user", userLogin);
            }
        } else {
            // 没有existUserId，说明之前没有登录过
            logger.info("用户原本未登录，现在成功登陆：" + userIdStr);
            msg = Msg.success("成功登录", "user", userLogin);
        }

        //用servletContext判断登录状态，用session储存用户的信息
        if (msg.getCode() == ResultCode.SUCCESS) {
            // 登录成功，修改储存的sessionId
            servletContext.setAttribute(userIdStr, httpSession.getId());
            // 存储登录的用户信息
            userLogin.setPassword(null);
            modelMap.addAttribute(AppAttribute.LOGGED, userLogin);
        }
        return msg;
    }

    /**
     * @param user
     * @return 200：成功
     * @throws ActionFailException 账号已存在
     */
    @PostMapping(value = "/register")
    public Msg register(User user) throws Exception {
        logger.info("用户注册：" + user);
        User register = userService.register(user);
        return Msg.success();
    }


    /**
     * 检查用户是否已登录
     * @param session
     * @return
     */
    @GetMapping(value = "/isOnline")
    public Msg checkUserIsOnline(HttpSession session) {
        logger.trace("检查用户是否已登录");

        User user = (User) session.getAttribute(AppAttribute.LOGGED);
        if (user == null) {
            return Msg.success("isOnline", "false");
        } else {
            Msg success = Msg.success("isOnline", "true");
            success.addData("user", user);
            return success;
        }
    }

    /**
     * 获取当前用户的信息（用于页眉）
     * @param user
     * @return
     */
    @GetMapping(value = "/curUser")
    public Msg getCurUserInfo(@SessionAttribute(AppAttribute.LOGGED) User user) {
        logger.trace("获取当前用户的信息（用于页眉）");
        return Msg.success("user", user);
    }
}
