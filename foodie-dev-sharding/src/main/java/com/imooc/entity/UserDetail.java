package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Mengdl
 * @date 2022/06/09
 */
@Data
@ToString
@EqualsAndHashCode
@TableName(value = "user_detail")
public class UserDetail {
    private Long user_id;
    private String age;
    private String sex;
}
