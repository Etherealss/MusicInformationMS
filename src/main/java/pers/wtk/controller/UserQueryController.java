package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.pojo.po.User;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.UserService;
import pers.wtk.utils.CodeUtil;

import java.util.List;

/**
 * @author wtk
 * @description <strong>获取</strong>用户
 * @date 2021-06-17
 */
@Controller
@ResponseBody
@RequestMapping("/users")
public class UserQueryController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private UserService userService;

    /**
     * 查找指定用户
     * @param userId
     * @return
     * @throws NotFoundException 用户不存在
     */
    @GetMapping(value = "/{userId}")
    public Msg findUser(@PathVariable("userId") Long userId) throws Exception {
        logger.trace("查询指定用户");
        User user = userService.getUserInfo(userId);
        return Msg.success("user", user);
    }

    /**
     * 获取分页用户
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @param userId
     * @param userName
     * @param userName
     * @return
     * @throws ActionFailException 当前页码不合法，如小于1或者大于最大页码数
     * @throws Exception
     */
    @GetMapping(params = {"p", "offset"})
    public Msg findUsers(@RequestParam("p") int curPage,
                          @RequestParam("offset") int offset,
                          @RequestParam(value = "id", required = false) Long userId,
                          @RequestParam(value = "name", required = false) String userName) throws Exception {

        logger.trace("获取分页用户");
        // encodeUrlComponent解码
        userName = CodeUtil.decodeUrlConponent(userName);

        if (curPage < 1) {
            curPage = 1;
        }
        if (offset <= 0) {
            offset = 10;
        }
        Page<User> pageUser = userService.getPageUser(curPage, offset, userId, userName);
        return Msg.success("page", pageUser);
    }
}
