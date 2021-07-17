package pers.wtk.dao.base;

import org.apache.ibatis.annotations.Param;
import pers.wtk.pojo.po.Music;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-17
 */
public interface BaseDao<T> {

    /**
     * 获取记录条数
     * @return
     */
    long size();


    T getOne(long id);

    /**
     * 更新
     * @param t
     * @return
     */
    boolean updateMusic(T t);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteOne(long id);

    /**
     * 删除多条
     * @param ids
     * @return
     */
    boolean deleteMany(Long[] ids);


    /**
     * 插入
     * @param t
     */
    boolean insertOne(T t);


    List<T> getRange(int start, int offset);


}
