package pers.wtk.common.strategy.page;

import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 获取分页音乐的策略
 * @date 2021-06-16
 */
public abstract class PageStrategy4Music {

    protected MusicDao musicDao;

    public PageStrategy4Music(MusicDao musicDao) {
        this.musicDao = musicDao;
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
    public abstract List<Music> getPage(int start, int offset, String keyword);
}
