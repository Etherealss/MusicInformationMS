package pers.wtk.common.strategy.page.options.singer;

import pers.wtk.common.strategy.page.PageStrategy4Singer;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description 获取分页歌手，没有限制
 * @date 2021-06-16
 */
public class QueryAllSinger extends PageStrategy4Singer {
    public QueryAllSinger(SingerDao singerDao) {
        super(singerDao);
    }

    /**
     * 获取记录数
     * @return
     */
    @Override
    public int getTotalCount(String keyword) {
        return singerDao.getSingerSize();
    }

    /**
     * 查询多条歌手记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword 为空
     * @return
     */
    @Override
    public List<Singer> getPage(int start, int offset, String keyword) {
        return singerDao.getRangeSinger(start, offset);
    }
}
