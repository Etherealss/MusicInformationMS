package pers.wtk.common.strategy.page.mapper;

import java.util.List;

/**
 * @author wtk
 * @description 操作不同Dao对象的行为类（桥接模式）
 * @date 2021-07-17
 */
public abstract class DaoMapper<T> {

    protected int curPage;
    protected int start;
    protected int offset;
    protected String keyword;

    public DaoMapper(int curPage, int offset) {
        this.curPage = curPage;
        this.offset = offset;
        start = (curPage - 1) * offset;
    }

    public DaoMapper(int curPage, int offset, String keyword) {
        this.curPage = curPage;
        this.offset = offset;
        this.keyword = keyword;
        start = (curPage - 1) * offset;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<T> queryAll() {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }

    /**
     * 所有记录数目
     * @return
     */
    public int queryAllSize() {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }

    /**
     * 按id查询记录
     * @return
     */
    public T queryById() {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }

    /**
     * 按name查询记录
     * @return
     */
    public List<T> queryByName() {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }

    /**
     * 按name查询的记录数目
     * @return
     */
    public int queryByNameSize() {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }


    /**
     * 按传入的字段名查询记录
     * @param fieldName
     * @return
     */
    public List<T> queryByFieldName(String fieldName) {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }

    /**
     * 按传入的字段名查询到的记录数目
     * @return
     * @param fieldName
     */
    public int queryByFieldNameSize(String fieldName) {
        throw new UnsupportedOperationException("不支持的数据查询方式！");
    }
}
