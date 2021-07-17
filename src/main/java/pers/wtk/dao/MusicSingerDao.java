package pers.wtk.dao;

import org.apache.ibatis.annotations.Param;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
public interface MusicSingerDao {

    /**
     * 为歌手和歌曲添加关系
     * @param musicId
     * @param singers
     * @return
     */
    boolean insertSingers4Music(@Param("musicId") long musicId,
                                @Param("singers") List<Singer> singers);

    /**
     * 为一个歌手和一首歌曲添加关系
     * @param musicId
     * @param singerId
     * @return
     */
    boolean insertMusicSinger(@Param("musicId") long musicId,
                              @Param("singerId") long singerId);
}
