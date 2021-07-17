package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.exception.specific.ParametersErrorException;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.service.SingerService;
import pers.wtk.service.SingerService;

/**
 * @author wtk
 * @description
 * @date 2021-06-17
 */
@Controller
@ResponseBody
@RequestMapping("/singers")
public class SingerController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private SingerService singerService;

    /**
     * 新增指定歌手
     * @param singer
     * @return
     */
    @PostMapping()
    public Msg addSinger(@RequestBody Singer singer) throws Exception {
        logger.trace("新增指定歌手：{}", singer);
        singerService.insertSinger(singer);
        return Msg.success("singer", singer);
    }

    /**
     * 更新
     * @param singer
     * @return
     */
    @PutMapping(value = "/{singerId}")
    public Msg updateSinger(@RequestBody Singer singer) throws Exception {
        logger.trace("更新歌手：" + singer);
        singerService.updateSinger(singer);
        return Msg.success();
    }

    /**
     * 删除
     * @param singerId
     * @return
     */
    @DeleteMapping(value = "/{singerId}")
    public Msg deleteSinger(@PathVariable Long singerId) throws Exception {
        logger.trace("删除歌手:" + singerId);
        singerService.deleteSinger(singerId);
        return Msg.success();
    }

    /**
     * 删除多条
     * @param singerIds 歌手ID字符串，用逗号分隔
     * @return
     */
    @DeleteMapping(params = {"ids"})
    public Msg deleteSinger(@RequestParam("ids") String singerIds) throws Exception {
        logger.trace("删除多个歌手:" + singerIds);
        Long[] ids;
        try {
            String[] split = singerIds.split(",");
            if (split.length == 0) {
                // 没有参数
                throw new ParametersErrorException("删除多条歌手，参数缺失");
            }
            ids = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                // 将字符串转为数字
                ids[i] = Long.parseLong(split[i]);
            }
        } catch (Exception e) {
            throw new ParametersErrorException("删除多条歌手，参数错误：" + e.getMessage());
        }
        singerService.deleteMultiSingers(ids);
        return Msg.success();
    }
}
