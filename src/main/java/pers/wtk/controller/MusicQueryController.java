package pers.wtk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.vo.Msg;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.MusicService;
import pers.wtk.utils.CodeUtil;

import javax.smartcardio.Card;
import java.util.List;

/**
 * @author wtk
 * @description <strong>获取</strong>音乐
 * @date 2021-06-17
 */
@Controller
@ResponseBody
@RequestMapping("/musics")
public class MusicQueryController {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private MusicService musicService;

    /**
     * 查找指定音乐
     * @param musicId
     * @return
     * @throws NotFoundException 音乐不存在
     */
    @GetMapping(value = "/{musicId}")
    public Msg findMusic(@PathVariable("musicId") Long musicId) throws Exception {
        logger.trace("查询指定音乐");
        Music music = musicService.getMusic(musicId);
        return Msg.success("music", music);
    }

    /**
     * 查询所有音乐
     * @return
     * @throws Exception
     */
    @GetMapping()
    public Msg findAllMusic() throws Exception {
        logger.trace("查询所有音乐");
        List<Music> allMusic = musicService.getAllMusic();
        return Msg.success("musics", allMusic);
    }

    /**
     * 根据音乐名模糊查找
     * @param musicName
     * @return
     * @throws Exception
     */
    @GetMapping(params = {"name"})
    public Msg findMusicByName(@RequestParam("name") String musicName) throws Exception {
        logger.trace("根据音乐名模糊查找");
        List<Music> musics = musicService.findMusicByName(musicName);
        return Msg.success("musics", musics);
    }

    /**
     * 根据歌手查找
     * @param singerName
     * @return
     * @throws Exception
     */
    @GetMapping(params = {"singer"})
    public Msg findMusicBySingerName(@RequestParam("singer") String singerName) throws Exception {
        logger.trace("根据歌手查找");
        List<Music> musics = musicService.findMusicBySingerName(singerName);
        return Msg.success("musics", musics);
    }

    /**
     * 获取分页音乐
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @param musicId
     * @param singerName
     * @param musicName
     * @return
     * @throws ActionFailException 当前页码不合法，如小于1或者大于最大页码数
     * @throws Exception
     */
    @GetMapping(params = {"p", "offset"})
    public Msg findMusics(@RequestParam("p") int curPage,
                          @RequestParam("offset") int offset,
                          @RequestParam(value = "id", required = false) Long musicId,
                          @RequestParam(value = "singer", required = false) String singerName,
                          @RequestParam(value = "name", required = false) String musicName) throws Exception {

        logger.trace("获取分页音乐：offset = {}", offset);
        // encodeUrlComponent解码
        singerName = CodeUtil.decodeUrlConponent(singerName);
        musicName = CodeUtil.decodeUrlConponent(musicName);

        if (curPage < 1) {
            curPage = 1;
        }
        if (offset <= 0) {
            offset = 10;
        }
        Page<Music> pageMusic = musicService.getPageMusic(curPage, offset, musicId, singerName, musicName);
        return Msg.success("page", pageMusic);
    }
}
