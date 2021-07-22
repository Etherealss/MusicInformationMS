package pers.wtk.common.strategy.page.query;

import pers.wtk.common.strategy.page.PageQueryStrategy;
import pers.wtk.common.strategy.page.mapper.DaoMapper;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-07-17
 */
public class QueryByNameStragegy<T> extends PageQueryStrategy<T> {

    public QueryByNameStragegy(DaoMapper<T> daoMapper) {
        super(daoMapper);
    }

    @Override
    protected int getTotalCount() {
        return this.daoMapper.queryByNameSize();
    }

    @Override
    protected List<T> getDataList() {
        return this.daoMapper.queryByName();
    }
}
