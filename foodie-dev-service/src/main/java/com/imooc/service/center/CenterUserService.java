package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.pojo.vo.center.OrderStatusCountVO;

/**
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:23
 */
public interface CenterUserService {
    /**
     * 查询用户信息
     * @param userId 用户id
     * @return 用户结果
     */
    Users queryUserInfo(String userId);

    /**
     * 用户修改信息
     * @param userId 用户id
     * @param centerUserBO 修改内容
     * @return 用户信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 更新用户的头像
     *
     * @param userId 用户id
     * @param userFaceUrl 用户头像路径
     * @return 返回对象
     */
    Users updateUserFace(String userId, String userFaceUrl);

}
