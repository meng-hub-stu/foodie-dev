package com.imooc.service.impl.center;

import com.imooc.enums.CommentLevel;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.center.CenterMapper;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.pojo.vo.center.OrderStatusCountVO;
import com.imooc.service.center.CenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:23
 */
@Service
public class CenterServiceImpl implements CenterService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO, users);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfo(userId);
    }

    @Override
    public OrderStatusCountVO queryUserOrdersStatusCounts(String userId) {
        //待付款
        Integer waitPayCounts = centerMapper.selectWaitCounts(userId, OrderStatusEnum.WAIT_PAY.type);
        //待发货
        Integer waitDeliverCounts = centerMapper.selectWaitCounts(userId, OrderStatusEnum.WAIT_DELIVER.type);
        //待收货
        Integer waitReceiveCounts = centerMapper.selectWaitCounts(userId, OrderStatusEnum.WAIT_RECEIVE.type);
        //待评价
        Integer waitCommentCounts = centerMapper.selectWaitCommentCounts(userId, OrderStatusEnum.SUCCESS.type, YesOrNo.NO.type);
        OrderStatusCountVO result = new OrderStatusCountVO();
        return null;
    }

    @Override
    public Users updateUserFace(String userId, String userFaceUrl) {
        Users users = new Users();
        users.setId(userId);
        users.setUpdatedTime(new Date());
        users.setFace(userFaceUrl);
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfo(userId);
    }

}
