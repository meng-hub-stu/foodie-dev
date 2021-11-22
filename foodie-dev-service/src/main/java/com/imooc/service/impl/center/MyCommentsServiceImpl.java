package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsCustomMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemCommentsBO;
import com.imooc.service.BaseService;
import com.imooc.service.center.MyComentsService;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mengdexin
 * @date 2021 -11 -21 -11:36
 */
@Service
public class MyCommentsServiceImpl extends BaseService implements MyComentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsCustomMapper itemsCommentsCustomMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Map map = new HashMap<>(1);
        map.put("userId", userId);
        return setterPagedGridResult(page,  itemsCommentsCustomMapper.queryMyComments(map));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveItemComments(String userId, String orderId, List<OrderItemCommentsBO> commentsList) {
        //保存商品的评价
        for(OrderItemCommentsBO ors : commentsList){
            ors.setCommentId(sid.nextShort());
        }
        Map map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("commentsList", commentsList);
        itemsCommentsCustomMapper.saveComments(map);
        //修改订单的评价状态
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(orders);
        //修改订单状态的评价时间

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        return true;
    }

}
