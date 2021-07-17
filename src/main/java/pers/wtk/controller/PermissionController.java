package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.wtk.dao.PermissionDao;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.service.MusicService;
import pers.wtk.service.PermissionService;

import java.util.List;

/**
 * @author wtk
 * @description 权限
 * @date 2021-06-20
 */
@Controller
@ResponseBody
@RequestMapping("/permissions")
public class PermissionController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private PermissionService permissionService;


    /**
     * 查询所有权限
     * @return
     * @throws Exception
     */
    @GetMapping()
    public Msg findAllMusic() throws Exception {
        logger.trace("查询所有权限");
        List<String> allPermissions = permissionService.getAllPermissions();
        return Msg.success("permissions", allPermissions);
    }
}
