package pers.wtk.common.strategy.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.pojo.vo.Page;

import java.util.List;

/**
 * @author wtk
 * @description 分页策略 可以实现对多种数据的分页，例如音乐分页、歌手分页
 * 具体的分页查询方式，例如按音乐名查询歌手并分页、按歌手名查询音乐并分页，则交与子策略实现
 * @date 2021-06-17
 */
public abstract class PageStrategy<T> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 获取记录数
     * @param keyword
     * @return
     */
    protected abstract int getTotalCount(String keyword);

    /**
     * 查询多条记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword
     * @return
     */
    protected abstract List<T> getDataList(int start, int offset, String keyword);

    /**
     * 获取分页，属于工具方法
     * @param keyword 搜索关键词
     * @param curPage 当前页
     * @param offset 偏移量，即每一页显示的记录数
     * @return
     * @throws ActionFailException
     */
    public Page<T> bulidPage(String keyword, int curPage, int offset) throws ActionFailException {

        // 计算查询索引，其实页面是第1页，但数据库第一条记录的索引是0，所以减1
        int start = (curPage - 1) * offset;

        /*
        执行策略 获取请求目标范围内的歌手数。用于计算总页码
         */
        int totalCount = this.getTotalCount(keyword);

        // 计算总页码数
        // 如果 总记录数可以整除以每页显示的记录数，那么总页数就是它们的商
        // 否则 说明有几条数据要另开一页显示，总页数+1
        int totalPage = totalCount / offset;
        totalPage = totalCount % offset == 0 ? totalPage : totalPage + 1;
        if (curPage > totalPage) {
            logger.warn("当前页码超出最大页码，无匹配项目：当前页数：{}, 总页数：{}", curPage, totalPage);
            throw new ActionFailException("当前页码超出最大页码，无匹配项目");
        }

        /*
        执行策略，获取当前页的歌手数据
         */
        List<T> rangeSinger = this.getDataList(start, offset, keyword);

        // 包装页面信息
        Page<T> page = new Page<>(curPage, offset);
        page.setTotalCount(totalCount);
        page.setDataList(rangeSinger);
        page.setTotalPage(totalPage);

        return page;
    }
}
