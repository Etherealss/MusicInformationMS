package pers.wtk.common.strategy.page.options.singer;

import pers.wtk.common.strategy.page.PageStrategy4Singer;
import pers.wtk.dao.SingerDao;
import pers.wtk.pojo.po.Singer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @description 通过歌手ID获取
 * @date 2021-06-16
 */
public class QuerySingerBySingerId extends PageStrategy4Singer {
    public QuerySingerBySingerId(SingerDao singerDao) {
        super(singerDao);
    }

    @Override
    public int getTotalCount(String singerId) {
        return 1;
    }

    @Override
    public List<Singer> getPage(int start, int offset, String singerIdStr) {
        List<Singer> list = new ArrayList<>(1);
        list.add(singerDao.getSinger(Long.parseLong(singerIdStr)));
        return list;
    }
}
