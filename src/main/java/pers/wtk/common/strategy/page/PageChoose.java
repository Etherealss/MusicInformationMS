package pers.wtk.common.strategy.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.strategy.page.mapper.DaoMapper;
import pers.wtk.common.strategy.page.query.QueryAllStragegy;
import pers.wtk.common.strategy.page.query.QueryByIdStragegy;
import pers.wtk.common.strategy.page.query.QueryByNameStragegy;
import pers.wtk.pojo.vo.Page;

/**
 * @author wtk
 * @description 分页策略选择类，等价于策略模式中的上下文类
 * @date 2021-06-16
 */
public class PageChoose<T> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略
     */
    private PageQueryStrategy<T> pageQueryStrategy;

    /**
     * 选择查询策略
     * @param strategy <div>
     * <li>QueryPageMusicStrategy：音乐分页</li>
     * <li>QueryPageSingerStrategy：歌手分页</li>
     * </div>
     */
    public PageChoose(PageQueryStrategy<T> strategy) {
        this.pageQueryStrategy = strategy;
    }

    /**
     * 获取Page分页对象
     * @return
     */
    public Page<T> getPage() throws ActionFailException {
        return pageQueryStrategy.bulidPage();
    }

    /**
     * 策略选择
     * @param id
     * @param name
     * @param daoMapper
     * @param <T>
     * @return
     */
    public static <T> PageChoose<T> buildPageChoose(Long id, String name, DaoMapper<T> daoMapper) {
        PageChoose<T> pageChoose;
        if (id != null && id > 0) {
            // 按id搜索
            daoMapper.setKeyword(String.valueOf(id));
            pageChoose = new PageChoose<>(new QueryByIdStragegy<>(daoMapper));
        }
        else if (name != null && name.length() > 0) {
            // 按name搜索
            daoMapper.setKeyword(name);
            pageChoose = new PageChoose<>(new QueryByNameStragegy<>(daoMapper));
        }
        else {
            // 搜索所有
            pageChoose = new PageChoose<>(new QueryAllStragegy<>(daoMapper));
        }
        return pageChoose;
    }
}
