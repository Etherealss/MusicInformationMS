package pers.wtk.service;

import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.vo.Page;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
public interface SingerService {

    /**
     * 新增
     * @param singer
     * @throws Exception
     */
    void insertSinger(Singer singer) throws Exception;

    /**
     * 获取
     * @param singerId
     * @return
     * @throws Exception
     */
    Singer getSinger(long singerId) throws Exception;

    /**
     * 获取分页歌手
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @param singerId
     * @param singerName
     * @return
     * @throws ActionFailException 当前页码不合法，如小于1或者大于最大页码数
     * @throws Exception
     */
    Page<Singer> getPageSinger(int curPage, int offset, Long singerId, String singerName) throws Exception;

    /**
     * 删除
     * @param singerId
     * @throws Exception
     */
    void deleteSinger(long singerId) throws Exception;

    /**
     * 更新
     * @param singer
     * @throws Exception
     */
    void updateSinger(Singer singer) throws Exception;

    /**
     * 按歌手名模糊查询
     * @param msuicName
     * @return
     * @throws Exception
     */
    List<Singer> findSingerByName(String msuicName) throws Exception;

    /**
     * 删除多条歌手
     * @param ids
     * @throws Exception
     */
    void deleteMultiSingers(Long[] ids) throws Exception;
}
