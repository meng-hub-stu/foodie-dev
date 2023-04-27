package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemCommentsBO;
import com.imooc.utils.PagedGridResult;
import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -21 -11:34
 */
public interface MyComentsService {

    /**
     * 查询个人订单评价列表
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 页数
     * @return 分页数据
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);

    /**
     * 查询商品列表
     * @param orderId 商品id
     * @return 返回订单商品
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 商品进行评价
     *
     * @param orderId 订单的id
     * @param userId 用户id
     * @param commentsList 商品评价内容
     * @return
     */
    boolean saveItemComments( String userId, String orderId, List<OrderItemCommentsBO> commentsList);

}
