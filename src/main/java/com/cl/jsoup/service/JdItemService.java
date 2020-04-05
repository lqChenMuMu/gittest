package com.cl.jsoup.service;

import com.cl.jsoup.pojo.JdItem;

import java.util.List;

/**
 * (JdItem)表服务接口
 *
 * @author chenlin
 * @since 2020-04-01 21:29:10
 */
public interface JdItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JdItem queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<JdItem> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    JdItem insert(JdItem jdItem);

    /**
     * 修改数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    JdItem update(JdItem jdItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    JdItem findBySkuId(Long skuId);

}