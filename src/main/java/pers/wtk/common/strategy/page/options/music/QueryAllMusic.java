package pers.wtk.common.strategy.page.options.music;

import pers.wtk.common.strategy.page.PageStrategy4Music;
import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 获取分页音乐，没有限制
 * @date 2021-06-16
 */
public class QueryAllMusic extends PageStrategy4Music {
    public QueryAllMusic(MusicDao musicDao) {
        super(musicDao);
    }

    /**
     * 获取记录数
     * @return
     */
    @Override
    public int getTotalCount(String keyword) {
        return musicDao.getMusicSize();
    }

    /**
     * 查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword 为空
     * @return
     */
    @Override
    public List<Music> getPage(int start, int offset, String keyword) {
        return musicDao.getRangeMusic(start, offset);
    }
}
