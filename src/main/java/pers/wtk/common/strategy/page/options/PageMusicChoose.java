package pers.wtk.common.strategy.page.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.strategy.page.PageStrategy;
import pers.wtk.common.strategy.page.PageStrategy4Music;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description 策略选择类，等价于策略模式中的上下文类
 * @date 2021-06-16
 */
public class PageMusicChoose extends PageStrategy<Music> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略
     */
    private PageStrategy4Music strategy;

    /**
     * 选择查询策略
     * @param strategy <div>
     * <li>QueryAllMusic：查询所有音乐，没有限制</li>
     * <li>QueryMusicByMusicId：通过音乐id查询</li>
     * <li>QueryMusicByMusicName：通过音乐名查询</li>
     * <li>QueryMusicBySingerName：通过音乐歌手名获取</li>
     * </div>
     */
    public PageMusicChoose(PageStrategy4Music strategy) {
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
     * 查询多条音乐记录
     * @param start 起始位置
     * @param offset 偏移量，即记录数
     * @param keyword
     * @return
     */
    @Override
    protected List<Music> getDataList(int start, int offset, String keyword) {
        return strategy.getPage(start, offset, keyword);
    }
}
