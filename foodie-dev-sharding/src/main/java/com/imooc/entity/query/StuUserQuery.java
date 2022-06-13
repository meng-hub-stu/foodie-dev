package com.imooc.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank;

/**
 * @author Mengdl
 * @date 2022/06/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询条件")
public class StuUserQuery extends BasePageQuery{

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @Override
    public <T> QueryWrapper<T> wrapperQuery() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(isNotBlank(userName), "user_name", userName);
        return queryWrapper;
    }

}
