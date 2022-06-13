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
@TableName(value = "user")
public class User {
    private Long id;
    private String name;
    private int userId;
}
