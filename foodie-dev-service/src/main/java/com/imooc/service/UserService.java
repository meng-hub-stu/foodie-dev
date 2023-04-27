package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

/**
 * 用户服务类
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:14
 */
public interface UserService {
    /**
     * 查询用户名是否存在
     * @param userName 用户名
     * @return 返回结果
     */
   boolean queryUsernameIsExist(String userName);

    /**
     * 创建用户
      * @param userBO
     * @return
     */
   Users createUser(UserBO userBO);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 返回用户
     */
   Users userLogin(String username, String password);

}
