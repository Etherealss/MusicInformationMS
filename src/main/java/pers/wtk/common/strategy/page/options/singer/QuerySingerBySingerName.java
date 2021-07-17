package pers.wtk.common.strategy.page.options.singer;

import pers.wtk.common.strategy.page.PageStrategy4Singer;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description 通过歌手名获取
 * @date 2021-06-16
 */
public class QuerySingerBySingerName extends PageStrategy4Singer {

    public QuerySingerBySingerName(SingerDao singerDao) {
        super(singerDao);
    }

    /**
     * 获取记录数
     * @param singerName
     * @return
     */
    @Override
    public int getTotalCount(String singerName) {
        return singerDao.getSingerSizeBySingerName(singerName);
    }

    /**
     * 查询多条歌手记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param singerName
     * @return
     */
    @Override
    public List<Singer> getPage(int start, int offset, String singerName) {
        return singerDao.getRangeSingerBySingerName(start, offset, singerName);
    }
}
