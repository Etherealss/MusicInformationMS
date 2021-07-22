package pers.wtk.common.strategy.page.query;

import pers.wtk.common.strategy.page.PageQueryStrategy;
import pers.wtk.common.strategy.page.mapper.DaoMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-07-17
 */
public class QueryByIdStragegy<T> extends PageQueryStrategy<T> {

    /**
     * {@inheritDoc}
     */
    public QueryByIdStragegy(DaoMapper<T> daoMapper) {
        super(daoMapper);
    }

    @Override
    protected int getTotalCount() {
        return 1;
    }

    @Override
    protected List<T> getDataList() {
        List<T> list = new ArrayList<>();
        list.add(daoMapper.queryById());
        return list;
    }
}
