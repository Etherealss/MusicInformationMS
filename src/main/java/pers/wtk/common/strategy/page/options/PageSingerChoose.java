package pers.wtk.common.strategy.page.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.strategy.page.PageStrategy;
import pers.wtk.common.strategy.page.PageStrategy4Singer;
import pers.wtk.pojo.po.Singer;

import java.util.List;

/**
 * @author wtk
 * @description 策略选择类，等价于策略模式中的上下文类
 * @date 2021-06-16
 */
public class PageSingerChoose extends PageStrategy<Singer> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略
     */
    private PageStrategy4Singer strategy;

    /**
     * 选择查询策略
     * @param strategy <div>
     * <li>QueryAllSinger：查询所有歌手，没有限制</li>
     * <li>QuerySingerBySingerId：通过歌手id查询</li>
     * <li>QuerySingerBySingerName：通过歌手名查询</li>
     * </div>
     */
    public PageSingerChoose(PageStrategy4Singer strategy) {
        this.strategy = strategy;
    }

    /**
     * 获取记录数
     * @return
     */
    @Override
    protected int getTotalCount(String keyword) {
        return strategy.getTotalCount(keyword);
    }

    /**
     * 查询多条歌手记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword
     * @return
     */
    @Override
    protected List<Singer> getDataList(int start, int offset, String keyword) {
        return strategy.getPage(start, offset, keyword);
    }
}
