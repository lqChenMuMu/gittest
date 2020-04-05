package com.cl.jsoup.service.impl;

import com.cl.jsoup.mapper.JdItemMapper;
import com.cl.jsoup.pojo.JdItem;
import com.cl.jsoup.service.JdItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (JdItem)表服务实现类
 *
 * @author chenlin
 * @since 2020-04-01 21:29:11
 */
@Service("jdItemService")
public class JdItemServiceImpl implements JdItemService {
    @Autowired
    private JdItemMapper jdItemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public JdItem queryById(Long id) {
        return this.jdItemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<JdItem> queryAllByLimit(int offset, int limit) {
        return this.jdItemDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    public JdItem insert(JdItem jdItem) {
        this.jdItemDao.insert(jdItem);
        return jdItem;
    }

    /**
     * 修改数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    public JdItem update(JdItem jdItem) {
        this.jdItemDao.update(jdItem);
        return this.queryById(jdItem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        return this.jdItemDao.deleteById(id) > 0;
    }

    /**
     * 通过skuId查询对象
     * @param skuId
     * @return
     */
    public JdItem findBySkuId(Long skuId) {
        return this.jdItemDao.queryBySkuId(skuId);
    }
}