package pers.wtk.dao;

import org.apache.ibatis.annotations.Param;
import pers.wtk.pojo.po.Lyric;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
public interface LyricDao {

    /**
     * 音乐id查找歌词
     * @param musicId
     * @return
     */
    List<Lyric> getLyricByMusic(long musicId);

    /**
     * 音乐和语言查找歌词
     * @param musicId
     * @param language
     * @return
     */
    Lyric getLyricByMusicAndLang(@Param("musicId") long musicId, @Param("language") String language);

    /**
     * 插入
     * @param lyric
     * @return
     */
    boolean insertLyric(Lyric lyric);

    /**
     * 删除
     * @param musicId
     * @param language
     * @return
     */
    boolean deleteLyric(@Param("musicId") String musicId, @Param("language") String language);

    /**
     * 更新
     * @param lyric
     * @return
     */
    boolean updateLyric(Lyric lyric);
}
