package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * 商品接口
 * @author Mengdl
 * @date 2021/11/11
 */
public interface ItemsService {
    /**
     * 商品信息
     * @param itemId 商品的id
     * @return 商品信息
     */
    Items queryItemById(String itemId);

    /**
     * 获取商品照片信息
     * @param itemId 商品id
     * @return 返回商品照片数据
     */
    List<ItemsImg> queryItemImageList(String itemId);

    /**
     * 商品的规格
     * @param itemId 商品的id
     * @return 商品规格数据
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 商品的参数
     * @param itemId 商品id
     * @return 商品参数数据
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 获取商品的等级
     * @param itemId 商品的id
     * @return 商品等级信息
     */
    CommentLevelCountsVO queryCommentLevel(String itemId);

    /**
     * 分页查询评价
     * @param itemId 商品的id
     * @param level 商品的等级
     * @param page 页码
     * @param pageSize 条数
     * @return 评价结果
     */
    PagedGridResult queryCommentByPage(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords 关键字
     * @param sort 排序
     * @param page 页码
     * @param pageSize 每页条数
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 刷新购物车商品的规格
     * @param itemSpecIds 商品规格的id拼接
     * @return 返回商品规格数据
     */
    List<ShopcartVO> queryItemsBySpecIds(String itemSpecIds);

    /**
     * 创建订单减少库存
     * @param specId 商品规格id
     * @param buyCounts 减去的数量
     */
    void decreaseItemSpecStork(String specId, int buyCounts);

    /**
     * 查询商品的规格数据
     * @param itemSpecId 规格id
     * @return 对象数据
     */
    ItemsSpec queryItemSpecById(String itemSpecId);

    /**
     * 获取商品的照片
     * @param itemId 商品id
     * @return 商品照片数据
     */
    ItemsImg queryItemImageByItemId(String itemId);

}
