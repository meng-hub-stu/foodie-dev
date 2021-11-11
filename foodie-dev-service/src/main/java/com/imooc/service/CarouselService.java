package com.imooc.service;

import com.imooc.pojo.Carousel;
import java.util.List;

/**
 * 首页商品轮播服务层
 * @Author Mengdexin
 * @date 2021 -11 -09 -21:18
 */
public interface CarouselService {
    /**
     * 查询所有的轮播图
     * @param isShow 是否展示
     * @return 返回结果
     */
    List<Carousel> queryAll(Integer isShow);

}
