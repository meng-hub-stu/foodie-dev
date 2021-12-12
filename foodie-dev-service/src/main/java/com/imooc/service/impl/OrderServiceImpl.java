package com.imooc.service.impl;

import com.google.common.collect.Lists;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.OrderBO;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.vo.MerchantOrderVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemsService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单实现类
 * @Author Mengdexin
 * @date 2021 -11 -12 -23:39
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final AddressService addressService;

    private final OrdersMapper ordersMapper;

    private final OrderStatusMapper orderStatusMapper;

    private final OrderItemsMapper orderItemsMapper;

    private final Sid sid;

    private final ItemsService itemsService;

    @Autowired
    public OrderServiceImpl(AddressService addressService,
                            OrdersMapper ordersMapper,
                            OrderStatusMapper orderStatusMapper,
                            OrderItemsMapper orderItemsMapper,
                            Sid sid,
                            ItemsService itemsService) {
        this.addressService = addressService;
        this.ordersMapper = ordersMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.sid = sid;
        this.itemsService = itemsService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderVO createOrder(OrderBO orderBO, List<ShopcartBO> shopcartList) {
        String addressId = orderBO.getAddressId();
        String itemSpecIds = orderBO.getItemSpecIds();
        String leftMsg = orderBO.getLeftMsg();
        Integer payMethod = orderBO.getPayMethod();
        String userId = orderBO.getUserId();
        Integer postAmount = 0;
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        //1.订单数据入库
        String ordersId = sid.nextShort();
        Orders orders = new Orders();
        orders.setId(ordersId);
        orders.setLeftMsg(leftMsg);
        orders.setPayMethod(payMethod);
        orders.setPostAmount(postAmount);
        orders.setUserId(userId);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverAddress(userAddress.getProvince() + " "
                + userAddress.getCity() + " "
                + userAddress.getDistrict() + " "
                + userAddress.getDetail());
        orders.setReceiverMobile(userAddress.getMobile());
        //2.订单的详情入库
        String[] itemSpecIdArr = itemSpecIds.split(",");
        Integer toTalAmount = 0;
        Integer realPayAmount = 0;
        List<ShopcartBO> toBeRemovedShopcateList = Lists.newArrayList();
        for (String itemSpecId : itemSpecIdArr) {
            // 后期整合redis，进行查询购买的数量
            ShopcartBO buyCountsFromShopcart = getBuyCountsFromShopcart(shopcartList, itemSpecId);
            Integer buyCount = 1;
            if(buyCountsFromShopcart != null){
                buyCount = buyCountsFromShopcart.getBuyCounts();
                toBeRemovedShopcateList.add(buyCountsFromShopcart);
            }
            //获取商品的规格数据
            ItemsSpec itemsSpec = itemsService.queryItemSpecById(itemSpecId);
            toTalAmount += itemsSpec.getPriceNormal() * buyCount;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCount;
            //根据商品id获取商品信息
            Items items = itemsService.queryItemById(itemsSpec.getItemId());
            ItemsImg itemsImg = itemsService.queryItemImageByItemId(itemsSpec.getItemId());
            //根据商品id获取商品的图片信息
            //2.1订单的详情入库
            OrderItems orderItems = new OrderItems();
            orderItems.setBuyCounts(buyCount);
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(ordersId);
            orderItems.setItemId(items.getId());
            orderItems.setItemImg(itemsImg != null ? itemsImg.getUrl() : null);
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setItemName(items.getItemName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);
            //用户提交之后，对应规格的商品进行扣除库存
            itemsService.decreaseItemSpecStork(itemSpecId, buyCount);
        }

        orders.setRealPayAmount(realPayAmount);
        orders.setTotalAmount(toTalAmount);
        ordersMapper.insert(orders);
        //3.订单的状态入库
        OrderStatus waitOrderStatus = new OrderStatus();
        waitOrderStatus.setOrderId(ordersId);
        waitOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitOrderStatus);
        //4，构建支付中心的数据
        MerchantOrderVO merchantOrderVO = new MerchantOrderVO();
        merchantOrderVO.setAmount(realPayAmount + postAmount);
        merchantOrderVO.setMerchantOrderId(ordersId);
        merchantOrderVO.setMerchantUserId(userId);
        merchantOrderVO.setPayMethod(payMethod);
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(ordersId);
        orderVO.setMerchantOrderVO(merchantOrderVO);
        orderVO.setToBeRemovedShopcatedList(toBeRemovedShopcateList);
        return orderVO;
    }

    /**
     * 在购物车获取商品的数量
      * @param shopcartList 购物车
     * @param itemSpecId 商品规格id
     * @return
     */
    private ShopcartBO getBuyCountsFromShopcart(List<ShopcartBO> shopcartList, String itemSpecId){
        for (ShopcartBO sc : shopcartList){
            if(sc.getSpecId().equals(itemSpecId)){
                return sc;
            }
        }
        return null;
    }

    @Override
    public void updateOrder(OrderBO orderBO) {

    }

    @Override
    public void deletedOrder(String userId, String orderId) {

    }

    @Override
    public void notifyMerchantOrderPaid(String merchantOrderId, Integer type) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(merchantOrderId);
        orderStatus.setPayTime(new Date());
        orderStatus.setOrderStatus(type);
        orderStatusMapper.updateByPrimaryKey(orderStatus);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public OrderStatus queryOrderStatus(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void autoCloseOrder() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> orderStatuses = orderStatusMapper.select(orderStatus);
        List<OrderStatus> orderResult = orderStatuses.stream().filter(v -> DateUtil.daysBetween(v.getCreatedTime(), DateUtil.getCurrentDateTime()) > 1).collect(Collectors.toList());
        orderResult.forEach(v ->{
            v.setOrderStatus(OrderStatusEnum.CLOSE.type);
            v.setCloseTime(DateUtil.getCurrentDateTime());
            orderStatusMapper.updateByPrimaryKey(v);
        });
    }
}
