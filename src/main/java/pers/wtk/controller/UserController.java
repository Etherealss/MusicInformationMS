package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.enums.AppAttribute;
import pers.wtk.common.enums.ResultCode;
import pers.wtk.common.exception.specific.ParametersErrorException;
import pers.wtk.pojo.po.User;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.service.PermissionService;
import pers.wtk.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wtk
 * @description 用户管理
 * @date 2021-06-09
 */
@Controller
@ResponseBody
@RequestMapping("users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 注销用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "cancellate", method = RequestMethod.POST)
    public Msg cancellateUser(@SessionAttribute(AppAttribute.LOGGED) long userId) throws Exception {
        logger.debug(String.valueOf(userId));
        userService.cancellateUser(userId);
        return Msg.success();
    }

    /**
     * 退出登录
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Msg logout(HttpSession httpSession) throws Exception {
        User user = (User)httpSession.getAttribute(AppAttribute.LOGGED);
        logger.info("退出登录：{}", user);
        httpSession.removeAttribute(AppAttribute.LOGGED);
        httpSession.getServletContext().removeAttribute(String.valueOf(user.getId()));
        return Msg.success();
    }


    /**
     * 更新普通信息
     * @param user
     * @return
     */
    @PutMapping(value = "/{userId}")
    public Msg updateUser(@RequestBody User user) throws Exception {
        logger.trace("更新用户：" + user);
        userService.updateUserInfo(user);
        return Msg.success();
    }

    /**
     * 更新用户权限
     * @param modelMap
     * @return
     */
    @PutMapping(value = "/permissions/{userId}")
    public Msg updateUserPermission(ModelMap modelMap, @PathVariable Long userId, @SessionAttribute User user) throws Exception {
        logger.trace("更新用户权限：" + userId);
        if (user.getId().equals(userId)) {
            // 当前用户修改自己的权限，不允许
            return new Msg(ResultCode.ACTION_FAIL, "不允许修改当前用户的权限");
        }
        List<String> addPerm = (List<String>) modelMap.get("addPerm");
        List<String> removePerm = (List<String>) modelMap.get("removePerm");
        permissionService.updateUserPermission(userId, addPerm, removePerm);
        return Msg.success();
    }

    /**
     * 删除
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/{userId}")
    public Msg deleteUser(@PathVariable Long userId) throws Exception {
        logger.trace("删除用户:" + userId);
        userService.cancellateUser(userId);
        return Msg.success();
    }

    /**
     * 删除多条
     * @param userIds 用户ID字符串，用逗号分隔
     * @return
     */
    @DeleteMapping(params = {"ids"})
    public Msg deleteUser(@RequestParam("ids") String userIds) throws Exception {
        logger.trace("删除多个用户:" + userIds);
        Long[] ids;
        try {
            String[] split = userIds.split(",");
            if (split.length == 0) {
                // 没有参数
                throw new ParametersErrorException("删除多条用户，参数缺失");
            }
            ids = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                // 将字符串转为数字
                ids[i] = Long.parseLong(split[i]);
            }
        } catch (Exception e) {
            throw new ParametersErrorException("删除多条用户，参数错误：" + e.getMessage());
        }
        userService.cancellateMultiUser(ids);
        return Msg.success();
    }
}
