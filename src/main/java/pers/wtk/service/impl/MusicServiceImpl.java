package pers.wtk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wtk.common.enums.LyricLanguage;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.exception.specific.TypeErrorException;
import pers.wtk.common.strategy.page.PageChoose;
import pers.wtk.common.strategy.page.mapper.DaoMapper;
import pers.wtk.common.strategy.page.mapper.MusicDaoMapper;
import pers.wtk.common.strategy.page.query.QueryAllStragegy;
import pers.wtk.common.strategy.page.query.QueryByIdStragegy;
import pers.wtk.common.strategy.page.query.QueryByNameStragegy;
import pers.wtk.common.strategy.page.query.QueryBySingerName4MusicStragegy;
import pers.wtk.dao.LyricDao;
import pers.wtk.dao.MusicDao;
import pers.wtk.dao.MusicSingerDao;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Lyric;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.MusicService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
@Service
public class MusicServiceImpl implements MusicService {

    private Logger logger = LoggerFactory.getLogger("root");

    @Autowired
    private MusicDao musicDao;
    @Autowired
    private LyricDao lyricDao;
    @Autowired
    private SingerDao singerDao;
    @Autowired
    private MusicSingerDao musicSingerDao;

    /**
     * 获取
     * @param musicId
     * @return
     * @throws NotFoundException 音乐不存在
     */
    @Override
    public Music getMusic(long musicId) throws NotFoundException {
        Music music = musicDao.getMusic(musicId);
        if (music == null) {
            throw new NotFoundException("音乐不存在");
        }
        setSingersAndLyric(music);
        return music;
    }

    /**
     * 获取
     * @return 所有音乐
     * @throws Exception
     */
    @Override
    public List<Music> getAllMusic() {
        return musicDao.getAllMusic();
    }

    @Override
    public Page<Music> getPageMusic(int curPage, int offset, Long musicId,
                                    String singerName, String musicName) throws Exception {
        logger.trace("分页搜索：curPage:{}, offset:{}, musicId:{}, singerName:{}, musicName:{}",
                curPage, offset, musicId, singerName, musicName);

        if (curPage < 0) {
            throw new ActionFailException("当前页码小于1，不合法");
        }

        /*
         策略模式选择
         */
        // 按音乐分页
        PageChoose<Music> pageChoose;
        DaoMapper<Music> daoMapper = new MusicDaoMapper(curPage, offset, musicDao);
        if (musicId != null && musicId > 0) {
            // 按musicId搜索
            daoMapper.setKeyword(String.valueOf(musicId));
            pageChoose = new PageChoose<>(new QueryByIdStragegy<>(daoMapper));
        }
        else if (singerName != null && singerName.length() > 0) {
            // 按singerName搜索
            daoMapper.setKeyword(singerName);
            pageChoose = new PageChoose<>(new QueryBySingerName4MusicStragegy<>(daoMapper));

        } else if (musicName != null && musicName.length() > 0) {
            // 按musicName搜索
            daoMapper.setKeyword(musicName);
            pageChoose = new PageChoose<>(new QueryByNameStragegy<>(daoMapper));
        } else {
            // 搜索所有
            pageChoose = new PageChoose<>(new QueryAllStragegy<>(daoMapper));
        }

        Page<Music> page = pageChoose.getPage();
        // 为音乐数据添加歌手和歌词
        List<Music> musicList = page.getDataList();
        if (singerName != null && singerName.length() > 0) {
            // 按歌手名搜索，歌手都是同一个
            setSingersAndLyric4SameSinger(musicList);
        } else {
            setSingersAndLyric(musicList);
        }
        return page;
    }

    /**
     * 按音乐名模糊查询
     * @param msuicName
     * @return
     * @throws Exception
     */
    @Override
    public List<Music> findMusicByName(String msuicName) {
        return musicDao.searchMusicByName(msuicName);
    }

    /**
     * 按歌手查询
     * @param singerName
     * @return
     * @throws Exception
     */
    @Override
    public List<Music> findMusicBySingerName(String singerName) throws Exception {
        List<Music> musics = musicDao.getMusicBySingerName(singerName);
        for (Music music : musics) {
            setSingersAndLyric(music);
        }
        return musics;
    }

    @Override
    public void deleteMultiMusics(Long[] ids) throws Exception {
        boolean s = musicDao.deleteMultiMusic(ids);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }

    /**
     * 新增
     * @param music
     * @throws Exception
     */
    @Override
    public void insertMusic(Music music) throws TypeErrorException, SQLException {
        Lyric lyric = music.getLyrics().get(0);

        // TODO 歌词语言校验待改进 使用遍历
        if (!(lyric.getLanguage().equals(LyricLanguage.CHINESE) ||
                lyric.getLanguage().equals(LyricLanguage.ENGLISH) ||
                lyric.getLanguage().equals(LyricLanguage.JAPANESE) ||
                lyric.getLanguage().equals(LyricLanguage.OTHERS))) {
            throw new TypeErrorException("错误的歌词语言");
        }
        // TODO 音乐媒体文件路径
        music.setMediaFilePath("/musicdata/" + music.getName() + ".mp3");
        musicDao.insertMusic(music);
        List<Singer> singers = music.getSingers();
        for (Singer singer : singers) {
            Singer singerByName = singerDao.getSingerByName(singer.getSingerName());
            Music music1 = musicDao.getMusic(music.getId());
            boolean insert = musicSingerDao.insertMusicSinger(music.getId(), singerByName.getId());
            if (!insert) {
                throw new SQLException("插入歌曲的歌手时失败");
            }
        }

        lyric.setMusicId(music.getId());
        lyricDao.insertLyric(lyric);
    }

    /**
     * 删除
     * @param musicId
     * @throws Exception
     */
    @Override
    public void deleteMusic(long musicId) throws NotFoundException {
        boolean s = musicDao.deleteMusic(musicId);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }

    /**
     * 更新
     * @param music
     * @throws Exception
     */
    @Override
    public void updateMusic(Music music) throws NotFoundException {
        // TODO 音乐媒体文件路径
        music.setMediaFilePath("/musicdata/" + music.getName() + ".mp3");
        boolean s = musicDao.updateMusic(music);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }

    /**
     * 添加歌手数据和歌词数据
     * 在查询到Music数据之后，由于歌手数据和歌词数据都在其他表中，
     * 所以还需要调用其他DAO设置属性
     * @param musicList
     */
    private void setSingersAndLyric(List<Music> musicList) {
        for (Music music : musicList) {
            setSingersAndLyric(music);
        }
    }

    private void setSingersAndLyric(Music music) {
        List<Singer> singers = singerDao.getSingersByMusicId(music.getId());
        music.setSingers(singers);

        List<Lyric> lyric = lyricDao.getLyricByMusic(music.getId());
        music.setLyrics(lyric);
    }

    /**
     * 为同一个歌手的歌曲添加歌手数据和歌词
     * @param musicList
     */
    private void setSingersAndLyric4SameSinger(List<Music> musicList) {
        Music fitstMusic = musicList.get(0);
        List<Singer> singers = singerDao.getSingersByMusicId(fitstMusic.getId());

        for (Music music : musicList) {
            // 歌手都是同一个
            music.setSingers(singers);
            List<Lyric> lyric = lyricDao.getLyricByMusic(music.getId());
            music.setLyrics(lyric);
        }
    }

}
