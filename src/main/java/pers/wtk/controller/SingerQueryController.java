package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.SingerService;
import pers.wtk.utils.CodeUtil;

import java.util.List;

/**
 * @author wtk
 * @description <strong>获取</strong>歌手
 * @date 2021-06-17
 */
@Controller
@ResponseBody
@RequestMapping("/singers")
public class SingerQueryController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private SingerService singerService;

    /**
     * 查找指定歌手
     * @param singerId
     * @return
     * @throws NotFoundException 歌手不存在
     */
    @GetMapping(value = "/{singerId}")
    public Msg findSinger(@PathVariable("singerId") Long singerId) throws Exception {
        logger.trace("查询指定歌手");
        Singer singer = singerService.getSinger(singerId);
        return Msg.success("singer", singer);
    }

    /**
     * 根据歌手名模糊查找
     * @param singerName
     * @return
     * @throws Exception
     */
    @GetMapping(params = {"name"})
    public Msg findSingerByName(@RequestParam("name") String singerName) throws Exception {
        logger.trace("根据歌手名模糊查找");
        List<Singer> singers = singerService.findSingerByName(singerName);
        return Msg.success("singers", singers);
    }

    /**
     * 获取分页歌手
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @param singerId
     * @param singerName
     * @param singerName
     * @return
     * @throws ActionFailException 当前页码不合法，如小于1或者大于最大页码数
     * @throws Exception
     */
    @GetMapping(params = {"p", "offset"})
    public Msg findSingers(@RequestParam("p") int curPage,
                          @RequestParam("offset") int offset,
                          @RequestParam(value = "id", required = false) Long singerId,
                          @RequestParam(value = "name", required = false) String singerName) throws Exception {

        logger.trace("获取分页歌手");
        // encodeUrlComponent解码
        singerName = CodeUtil.decodeUrlConponent(singerName);

        if (curPage < 1) {
            curPage = 1;
        }
        if (offset <= 0) {
            offset = 10;
        }
        Page<Singer> pageSinger = singerService.getPageSinger(curPage, offset, singerId, singerName);
        return Msg.success("page", pageSinger);
    }
}
