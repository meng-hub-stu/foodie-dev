package com.imooc.service.impl.center;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.center.MyOrderVO;
import com.imooc.pojo.vo.center.OrderStatusCountVO;
import com.imooc.service.BaseService;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -20 -13:45
 */
@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public PagedGridResult queryOrderData(String userId, String orderStatus, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return setterPagedGridResult(page, ordersMapperCustom.selectOrderData(userId, orderStatus));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateOrderStatusByOrderId(String orderId, Integer type) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        if(OrderStatusEnum.WAIT_RECEIVE.type.equals(type)){
            orderStatus.setDeliverTime(new Date());
        } else if(OrderStatusEnum.SUCCESS.type.equals(type)){
            orderStatus.setSuccessTime(new Date());
        } else {

        }
        orderStatus.setOrderStatus(type);
        return orderStatusMapper.updateByPrimaryKeySelective(orderStatus) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Orders queryMyOrders(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setIsDelete(YesOrNo.NO.type);
        return ordersMapper.selectOne(orders);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteOrderByUserIdAndOrderId(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(orders);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatus.setOrderId(orderId);
        orderStatus.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKey(orderStatus);
        return true;
    }

    @Override
    public PagedGridResult queryMyOrdersTrend(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return setterPagedGridResult(page, ordersMapperCustom.quertMyOrdersTrend(userId));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public OrderStatusCountVO queryUserOrdersStatusCounts(String userId) {
        //待付款
        Integer waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(userId, OrderStatusEnum.WAIT_PAY.type, null);
        //待发货
        Integer waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(userId, OrderStatusEnum.WAIT_DELIVER.type, null);
        //待收货
        Integer waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(userId, OrderStatusEnum.WAIT_RECEIVE.type, null);
        //待评价
        Integer waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(userId, OrderStatusEnum.SUCCESS.type, YesOrNo.NO.type);
        OrderStatusCountVO result = new OrderStatusCountVO(waitPayCounts, waitDeliverCounts, waitReceiveCounts, waitCommentCounts);
        return result;
    }

}
