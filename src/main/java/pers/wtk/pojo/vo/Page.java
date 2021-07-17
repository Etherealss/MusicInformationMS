package pers.wtk.pojo.vo;

import lombok.Data;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-11
 */
@Data
public class Page<T> {
    /** 总记录数 */
    private int totalCount;
    /** 总页码 */
    private int totalPage;
    /** 当前页码 */
    private int curPage;
    /** 偏移量，即每一页显示的记录数 */
    private int offset;
    /** 每页的数据 */
    private List<T> dataList;

    /**
     * @param curPage 当前页码
     * @param offset 偏移量，即每一页显示的记录数
     */
    public Page(int curPage, int offset) {
        this.curPage = curPage;
        this.offset = offset;
    }
}
