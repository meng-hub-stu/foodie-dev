package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

/**
 * 地址接口层
 * @author Mengdl
 * @date 2021/11/11
 */
public interface AddressService {
    /**
     * 查询所有的收获地址
     * @param userId  用户id
     * @return 返回地址信息
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 新增收货地址
     * @param addressBO 地址对象
     * @return 返回新增地址的id
     */
    String addNewUserAddress(AddressBO addressBO);

    /**
     * 修改地址
     * @param addressBO 入参对象
     */
    void updateUserAdress(AddressBO addressBO);

    /**
     * 删除地址
     * @param addressId 地址的id
     * @param userId 用户id
     */
    void deleteUserAddress(String userId,String addressId);

    /**
     * 设置为默认地址
     * @param addressId 地址的id
     * @param userId 用户id
     */
    void updateUserAddress(String userId,String addressId);

    /**
     * 查询用户的地址
     * @param userId 用户id
     * @param addressId 地址id
     * @return 结果集
     */
    UserAddress queryUserAddress(String userId, String addressId);

}
