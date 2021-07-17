package pers.wtk.common.strategy.page.options.music;

import pers.wtk.common.strategy.page.PageStrategy4Music;
import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 通过音乐名获取
 * @date 2021-06-16
 */
public class QueryMusicByMusicName extends PageStrategy4Music {

    public QueryMusicByMusicName(MusicDao musicDao) {
        super(musicDao);
    }

    /**
     * 获取记录数
     * @param musicName
     * @return
     */
    @Override
    public int getTotalCount(String musicName) {
        return musicDao.getMusicSizeByMusicName(musicName);
    }

    /**
     * 查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param musicName
     * @return
     */
    @Override
    public List<Music> getPage(int start, int offset, String musicName) {
        return musicDao.getRangeMusicByMusicName(start, offset, musicName);
    }
}
