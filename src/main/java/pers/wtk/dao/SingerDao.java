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
public interface SingerDao {

    /**
     * 获取记录条数
     * @return
     */
    int getSingerSize();

    /**
     * 获取记录条数
     * @return
     */
    int getSingerSizeBySingerName(String singerName);

    /**
     * 根据音乐查找歌手
     * @param musicId
     * @return
     */
    List<Singer> getSingersByMusicId(long musicId);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @return
     */
    List<Singer> getRangeSinger(@Param("start") int start, @Param("offset") int offset);

    /**
     * 获取[start, offset)区间内的数据
     * 使用sql limit查询，第一条数据的start为0
     * @param start
     * @param offset
     * @param singerName
     * @return
     */
    List<Singer> getRangeSingerBySingerName(@Param("start") int start, @Param("offset") int offset,
                                            @Param("singerName") String singerName);

    /**
     * 获取歌手
     * @param singerId
     * @return
     */
    Singer getSinger(long singerId);

    /**
     * 通过姓名获取歌手
     * @param singerName
     * @return
     */
    Singer getSingerByName(String singerName);

    /**
     * 歌手名 模糊搜索
     * @param keyword
     * @return
     */
    List<Singer> searchSingerByName(String keyword);

    /**
     * 插入
     * @param singer
     */
    boolean insertSinger(Singer singer);

    /**
     * 删除
     * @param singerId
     * @return
     */
    boolean deleteSinger(long singerId);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean deleteMultiSinger(@Param("ids") Long[] ids);

    /**
     * 更新
     * @param singer
     * @return
     */
    boolean updateSinger(Singer singer);
}
