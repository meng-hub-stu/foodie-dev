package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 收货地址控制类
 * @author Mengdl
 * @date 2021/11/11
 */
@RestController
@RequestMapping(value = "address")
@Api(value = "地址管理", tags = {"用户地址相关接口"})
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(value = "list")
    @ApiOperation(value = "用户地址列表", notes = "用户的id", httpMethod = "GET")
    public IMOOCJSONResult list(@RequestParam(value = "userId") String userId){
        List<UserAddress> result = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(result);
    }

    @PostMapping(value = "add")
    @ApiOperation(value = "用户新增地址", notes = "用户数据和地址信息", httpMethod = "POST")
    public IMOOCJSONResult add(@Valid @RequestBody AddressBO addressBO){
        return IMOOCJSONResult.ok(addressService.addNewUserAddress(addressBO));
    }

    @PostMapping(value = "update")
    @ApiOperation(value = "修改地址", notes = "修改地址", httpMethod = "PUT")
    public IMOOCJSONResult update(@Valid @RequestBody AddressBO addressBO){
        if(StringUtils.isBlank(addressBO.getAddressId())){
            return IMOOCJSONResult.errorMsg("地址id不能为空");
        }
        addressService.updateUserAdress(addressBO);
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "delete")
    @ApiOperation(value = "删除地址", notes = "删除地址", httpMethod = "DELETE")
    public IMOOCJSONResult delete(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "addressId") String addressId){
        addressService.deleteUserAddress(userId, addressId);
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "setDefalut")
    @ApiOperation(value = "设置默认地址", notes = "设置默认地址", httpMethod = "POST")
    public IMOOCJSONResult update(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "addressId") String addressId){
        addressService.updateUserAddress(userId, addressId);
        return IMOOCJSONResult.ok();
    }

}
