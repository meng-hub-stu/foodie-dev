package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.pojo.vo.center.OrderStatusCountVO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.PagedGridResult;
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
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

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
    public Users updateUserFace(String userId, String userFaceUrl) {
        Users users = new Users();
        users.setId(userId);
        users.setUpdatedTime(new Date());
        users.setFace(userFaceUrl);
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfo(userId);
    }

}
