package pers.wtk.common.strategy.page.mapper;

import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-07-18
 */
public class SingerDaoMapper extends DaoMapper<Singer> {

    private SingerDao singerDao;

    public SingerDaoMapper(int curPage, int offset, SingerDao singerDao) {
        super(curPage, offset);
        this.singerDao = singerDao;
    }

    public SingerDaoMapper(int curPage, int offset, String keyword, SingerDao singerDao) {
        super(curPage, offset, keyword);
        this.singerDao = singerDao;
    }

    @Override
    public List<Singer> queryAll() {
        return singerDao.getRangeSinger(start, offset);
    }

    @Override
    public int queryAllSize() {
        return singerDao.getSingerSize();
    }

    @Override
    public Singer queryById() {
        return singerDao.getSinger(Long.parseLong(keyword));
    }

    @Override
    public List<Singer> queryByName() {
        return singerDao.getRangeSingerBySingerName(start, offset, keyword);
    }

    @Override
    public int queryByNameSize() {
        return singerDao.getSingerSizeBySingerName(keyword);
    }
}
