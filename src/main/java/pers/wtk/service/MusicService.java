package pers.wtk.service;

import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.vo.Page;
import pers.wtk.common.exception.specific.ActionFailException;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
public interface MusicService {

    /**
     * 新增
     * @param music
     * @throws Exception
     */
    void insertMusic(Music music) throws Exception;

    /**
     * 获取
     * @param musicId
     * @return
     * @throws Exception
     */
    Music getMusic(long musicId) throws Exception;

    /**
     * 获取
     * @return 所有音乐
     * @throws Exception
     */
    List<Music> getAllMusic() throws Exception;

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
    Page<Music> getPageMusic(int curPage, int offset, Long musicId,
                             String singerName, String musicName) throws Exception;

    /**
     * 删除
     * @param musicId
     * @throws Exception
     */
    void deleteMusic(long musicId) throws Exception;

    /**
     * 更新
     * @param music
     * @throws Exception
     */
    void updateMusic(Music music) throws Exception;

    /**
     * 按音乐名模糊查询
     * @param msuicName
     * @return
     * @throws Exception
     */
    List<Music> findMusicByName(String msuicName) throws Exception;

    /**
     * 按歌手查询
     * @param singerName
     * @return
     * @throws Exception
     */
    List<Music> findMusicBySingerName(String singerName) throws Exception;

    /**
     * 删除多条音乐
     * @param ids
     * @throws Exception
     */
    void deleteMultiMusics(Long[] ids) throws Exception;
}
