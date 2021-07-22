package pers.wtk.common.strategy.page.query;

import pers.wtk.common.strategy.page.PageQueryStrategy;
import pers.wtk.common.strategy.page.mapper.DaoMapper;
import pers.wtk.common.strategy.page.mapper.MusicDaoMapper;

import java.util.List;

/**
 * @author wtk
 * @description 按歌手名查找音乐
 * @date 2021-07-17
 */
public class QueryBySingerName4MusicStragegy<Music> extends PageQueryStrategy<Music> {

    public QueryBySingerName4MusicStragegy(DaoMapper<Music> daoMapper) {
        super(daoMapper);
    }

    @Override
    protected int getTotalCount() {
        return this.daoMapper.queryByFieldNameSize("singerName");
    }

    @Override
    protected List<Music> getDataList() {
        return this.daoMapper.queryByFieldName("singerName");
    }
}
