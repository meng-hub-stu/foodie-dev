package com.imooc;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:14
 */
public interface UserService {
    /**
     * 查询用户名是否存在
     * @param userName 用户名
     * @return 返回结果
     */
   public boolean queryUsernameIsExist(String userName);

    /**
     * 创建用户
      * @param userBO
     * @return
     */
   public Users createUser(UserBO userBO);

}
