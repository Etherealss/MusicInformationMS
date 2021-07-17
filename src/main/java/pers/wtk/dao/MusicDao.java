package pers.wtk.dao;

import org.apache.ibatis.annotations.Param;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 音乐DAO
 * @date 2021-05-29
 */
public interface MusicDao {

    /**
     * 获取记录条数
     * @return
     */
    int getMusicSize();

    /**
     * 通过歌手名获取记录条数
     * @param singerName
     * @return
     */
    int getMusicSizeBySingerName(String singerName);

    /**
     * 通过音乐名获取记录条数
     * @param musicName
     * @return
     */
    int getMusicSizeByMusicName(String musicName);


    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @return
     */
    List<Music> getRangeMusic(@Param("start") int start, @Param("offset") int offset);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @param musicName
     * @return
     */
    List<Music> getRangeMusicByMusicName(@Param("start") int start, @Param("offset") int offset,
                                         @Param("musicName") String musicName);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @param singerName
     * @return
     */
    List<Music> getRangeMusicBySingerName(@Param("start") int start, @Param("offset") int offset,
                                          @Param("singerName") String singerName);

    /**
     * 获取单一数据
     * @param musicId
     * @return
     */
    Music getMusic(long musicId);

    /**
     * 获取所有
     * @return
     */
    List<Music> getAllMusic();

    /**
     * 音乐名 模糊搜索
     * @param musicName
     * @return
     */
    List<Music> searchMusicByName(String musicName);

    /**
     * 歌手名 模糊搜索
     * @param singerName
     * @return
     */
    List<Music> getMusicBySingerName(String singerName);

    /**
     * 更新
     * @param music
     * @return
     */
    boolean updateMusic(Music music);

    /**
     * 删除
     * @param musicId
     * @return
     */
    boolean deleteMusic(long musicId);

    /**
     * 删除多条
     * @param ids
     * @return
     */
    boolean deleteMultiMusic(@Param("ids") Long[] ids);


    /**
     * 插入
     * @param music
     */
    boolean insertMusic(Music music);
}
