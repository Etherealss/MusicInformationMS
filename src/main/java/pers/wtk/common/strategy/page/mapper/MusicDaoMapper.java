package pers.wtk.common.strategy.page.mapper;

import pers.wtk.dao.MusicDao;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-07-18
 */
public class MusicDaoMapper extends DaoMapper<Music> {

    private MusicDao musicDao;

    public MusicDaoMapper(int curPage, int offset, MusicDao musicDao) {
        super(curPage, offset);
        this.musicDao = musicDao;
    }

    public MusicDaoMapper(int curPage, int offset, String keyword, MusicDao musicDao) {
        super(curPage, offset, keyword);
        this.musicDao = musicDao;
    }

    @Override
    public List<Music> queryAll() {
        return musicDao.getRangeMusic(start, offset);
    }

    @Override
    public int queryAllSize() {
        return musicDao.getMusicSize();
    }

    @Override
    public Music queryById() {
        return musicDao.getMusic(Long.parseLong(keyword));
    }

    @Override
    public List<Music> queryByName() {
        return musicDao.getRangeMusicByMusicName(start, offset, keyword);
    }

    @Override
    public int queryByNameSize() {
        return musicDao.getMusicSizeByMusicName(keyword);
    }

    @Override
    public List<Music> queryByFieldName(String fieldName) {
        if ("singerName".equals(fieldName)) {
            return musicDao.getMusicBySingerName(keyword);
        }
        return null;
    }

    @Override
    public int queryByFieldNameSize(String fieldName) {
        if ("singerName".equals(fieldName)) {
            return musicDao.getMusicSizeBySingerName(keyword);
        }
        return 0;
    }
}
