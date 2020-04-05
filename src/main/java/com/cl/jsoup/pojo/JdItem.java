package com.cl.jsoup.pojo;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (JdItem)实体类
 *
 * @author chenlin
 * @since 2020-04-01 21:14:35
 */
@Data
public class JdItem implements Serializable {
    private static final long serialVersionUID = -11739612389784529L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 商品集合id
    */
    private Long spu;
    /**
    * 商品最小品类单元id
    */
    private Long sku;
    /**
    * 商品标题
    */
    private String title;
    /**
    * 商品价格
    */
    private Double price;
    /**
    * 商品图片
    */
    private String pic;
    /**
    * 商品详情地址
    */
    private String url;
    /**
    * 创建时间
    */
    private Date created;
    /**
    * 修改时间
    */
    private Date updated;


}