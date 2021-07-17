package pers.wtk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.strategy.page.PageChoose;
import pers.wtk.common.strategy.page.options.PageSingerChoose;
import pers.wtk.common.strategy.page.options.singer.QueryAllSinger;
import pers.wtk.common.strategy.page.options.singer.QuerySingerBySingerId;
import pers.wtk.common.strategy.page.options.singer.QuerySingerBySingerName;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Singer;
import pers.wtk.pojo.vo.Page;
import pers.wtk.service.SingerService;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-09
 */
@Service
public class SingerServiceImpl implements SingerService {

    private Logger logger = LoggerFactory.getLogger("root");

    @Autowired
    private SingerDao singerDao;

    /**
     * 获取
     * @param singerId
     * @return
     * @throws NotFoundException 歌手不存在
     */
    @Override
    public Singer getSinger(long singerId) throws NotFoundException {
        Singer singer = singerDao.getSinger(singerId);
        if (singer == null) {
            throw new NotFoundException("歌手不存在");
        }
        return singer;
    }

    /**
     * 获取分页歌手
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @param singerId
     * @param singerName
     * @return
     * @throws ActionFailException 当前页码不合法，如小于1或者大于最大页码数
     * @throws Exception
     */
    @Override
    public Page<Singer> getPageSinger(int curPage, int offset, Long singerId, String singerName) throws Exception {
        logger.trace("分页搜索：curPage:{}, offset:{}, singerId:{}, singerName:{}",
                curPage, offset, singerId, singerName);

        if (curPage < 0) {
            throw new ActionFailException("当前页码小于1，不合法");
        }

        /*
         策略模式选择
         */
        PageSingerChoose singerChoose;
        String keyword;
        if (singerId != null && singerId > 0) {
            // 按singerId搜索
            singerChoose = new PageSingerChoose(new QuerySingerBySingerId(singerDao));
            keyword = String.valueOf(singerId);
        } else if (singerName != null && singerName.length() > 0) {
            // 按singerName搜索
            singerChoose = new PageSingerChoose(new QuerySingerBySingerName(singerDao));
            keyword = singerName;
        } else {
            // 搜索所有
            singerChoose = new PageSingerChoose(new QueryAllSinger(singerDao));
            keyword = "";
        }
        // 按歌手分页
        PageChoose<Singer> pageChoose = new PageChoose<>(singerChoose);

        return pageChoose.getPage(keyword, curPage, offset);

    }

    /**
     * 按歌手名模糊查询
     * @param msuicName
     * @return
     * @throws Exception
     */
    @Override
    public List<Singer> findSingerByName(String msuicName) {
        return singerDao.searchSingerByName(msuicName);
    }

    @Override
    public void deleteMultiSingers(Long[] ids) throws Exception {
        boolean s = singerDao.deleteMultiSinger(ids);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }

    /**
     * 新增
     * @param singer
     * @throws Exception
     */
    @Override
    public void insertSinger(Singer singer) {
        singerDao.insertSinger(singer);
    }

    /**
     * 删除
     * @param singerId
     * @throws Exception
     */
    @Override
    public void deleteSinger(long singerId) throws NotFoundException {
        boolean s = singerDao.deleteSinger(singerId);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }

    /**
     * 更新
     * @param singer
     * @throws Exception
     */
    @Override
    public void updateSinger(Singer singer) throws NotFoundException {
        boolean s = singerDao.updateSinger(singer);
        // 如果更新失败，则应该是记录未查询到
        if (!s) {
            throw new NotFoundException();
        }
    }
}
