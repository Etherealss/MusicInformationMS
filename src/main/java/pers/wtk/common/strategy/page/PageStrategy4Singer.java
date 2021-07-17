package pers.wtk.common.strategy.page;

import pers.wtk.common.strategy.page.PageStrategy;
import pers.wtk.dao.MusicDao;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Music;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description 获取分页歌手的策略
 * @date 2021-06-16
 */
public abstract class PageStrategy4Singer {

    protected SingerDao singerDao;

    public PageStrategy4Singer(SingerDao singerDao) {
        this.singerDao = singerDao;
    }

    /**
     * 获取记录数
     * @param keyword
     * @return
     */
    public abstract int getTotalCount(String keyword);

    /**
     * 查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword
     * @return
     */
    public abstract List<Singer> getPage(int start, int offset, String keyword);
}
