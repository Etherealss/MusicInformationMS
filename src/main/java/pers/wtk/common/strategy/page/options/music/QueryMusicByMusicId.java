package pers.wtk.common.strategy.page.options.music;

import pers.wtk.common.strategy.page.PageStrategy4Music;
import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @description 通过音乐ID获取
 * @date 2021-06-16
 */
public class QueryMusicByMusicId extends PageStrategy4Music {
    public QueryMusicByMusicId(MusicDao musicDao) {
        super(musicDao);
    }

    /**
     * 获取记录数
     * @param musicId
     * @return
     */
    @Override
    public int getTotalCount(String musicId) {
        return 1;
    }

    /**
     * 查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param musicIdStr
     * @return
     */
    @Override
    public List<Music> getPage(int start, int offset, String musicIdStr) {
        List<Music> list = new ArrayList<>(1);
        list.add(musicDao.getMusic(Long.parseLong(musicIdStr)));
        return list;
    }
}
