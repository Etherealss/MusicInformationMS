package pers.wtk.common.strategy.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.pojo.vo.Page;

/**
 * @author wtk
 * @description 分页策略选择类，等价于策略模式中的上下文类
 * 可以实现对多种数据的分页，例如音乐分页、歌手分页
 * @date 2021-06-16
 */
public class PageChoose<T> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略，例如音乐分页、歌手分页
     */
    private PageStrategy<T> pageStrategy;

    /**
     * 选择查询策略
     * @param strategy <div>
     * <li>QueryPageMusicStrategy：音乐分页</li>
     * <li>QueryPageSingerStrategy：歌手分页</li>
     * </div>
     */
    public PageChoose(PageStrategy<T> strategy) {
        this.pageStrategy = strategy;
    }

    /**
     * 获取Page分页对象
     * @param keyword
     * @param curPage 当前页码
     * @param offset 偏移量，即记录数
     * @return
     */
    public Page<T> getPage(String keyword, int curPage, int offset) throws ActionFailException {
        return pageStrategy.bulidPage(keyword, curPage, offset);
    }
}
