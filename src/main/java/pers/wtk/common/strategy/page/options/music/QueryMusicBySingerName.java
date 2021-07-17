package pers.wtk.common.strategy.page.options.music;

import pers.wtk.common.strategy.page.PageStrategy4Music;
import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 通过歌手名获取
 * @date 2021-06-16
 */
public class QueryMusicBySingerName extends PageStrategy4Music {
    public QueryMusicBySingerName(MusicDao musicDao) {
        super(musicDao);
    }

    /**
     * 按歌手名获取记录数
     * @param singerName
     * @return
     */
    @Override
    public int getTotalCount(String singerName) {
        return musicDao.getMusicSizeBySingerName(singerName);
    }

    /**
     * 按歌手名查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param singerName
     * @return
     */
    @Override
    public List<Music> getPage(int start, int offset, String singerName) {
        return musicDao.getRangeMusicBySingerName(start, offset, singerName);
    }
}
