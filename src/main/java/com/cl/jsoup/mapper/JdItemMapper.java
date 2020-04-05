package com.cl.jsoup.mapper;

import com.cl.jsoup.pojo.JdItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (JdItem)表数据库访问层
 *
 * @author chenlin
 * @since 2020-04-01 21:21:55
 */
public interface JdItemMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JdItem queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<JdItem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jdItem 实例对象
     * @return 对象列表
     */
    List<JdItem> queryAll(JdItem jdItem);

    /**
     * 新增数据
     *
     * @param jdItem 实例对象
     * @return 影响行数
     */
    int insert(JdItem jdItem);

    /**
     * 修改数据
     *
     * @param jdItem 实例对象
     * @return 影响行数
     */
    int update(JdItem jdItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    JdItem queryBySkuId(@Param("skuId") Long skuId);
}