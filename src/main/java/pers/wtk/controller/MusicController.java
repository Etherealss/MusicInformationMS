package pers.wtk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.exception.specific.ParametersErrorException;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.service.MusicService;


/**
 * @author wtk
 * @description 管理音乐
 * @date 2021-06-09
 */
@Api(tags = "音乐管理接口")
@Controller
@ResponseBody
@RequestMapping("/musics")
public class MusicController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private MusicService musicService;

    /**
     * 新增指定音乐
     * @param music
     * @return
     */
    @ApiOperation("新增指定音乐")
    @PostMapping()
    public Msg addMusic(@RequestBody Music music) throws Exception {
        logger.trace("新增指定音乐：{}", music);
        musicService.insertMusic(music);
        return Msg.success("singer", music);
    }

    /**
     * 更新
     * @param music
     * @return
     */
    @ApiOperation("更新指定音乐")
    @PutMapping(value = "/{musicId}")
    public Msg updateMusic(@RequestBody Music music) throws Exception {
        logger.trace("更新音乐：" + music);
        musicService.updateMusic(music);
        return Msg.success();
    }

    /**
     * 删除
     * @param musicId
     * @return
     */
    @DeleteMapping(value = "/{musicId}")
    public Msg deleteMusic(@ApiParam(value = "音乐id", defaultValue = "1") @PathVariable Long musicId) throws Exception {
        logger.trace("删除音乐:" + musicId);
        musicService.deleteMusic(musicId);
        return Msg.success();
    }

    /**
     * 删除
     * @param musicIds 音乐ID字符串，用逗号分隔
     * @return
     */
    @DeleteMapping(params = {"ids"})
    public Msg deleteMusic(@ApiParam(value = "多个音乐id的字符串", defaultValue = "'1,2,3'")
                               @RequestParam("ids") String musicIds) throws Exception {
        logger.trace("删除多个音乐:" + musicIds);
        Long[] ids;
        try {
            String[] split = musicIds.split(",");
            if (split.length == 0) {
                // 没有参数
                throw new ParametersErrorException("删除多条音乐，参数缺失");
            }
            ids = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                // 将字符串转为数字
                ids[i] = Long.parseLong(split[i]);
            }
        } catch (Exception e) {
            throw new ParametersErrorException("删除多条音乐，参数错误：" + e.getMessage());
        }
        musicService.deleteMultiMusics(ids);
        return Msg.success();
    }
}
