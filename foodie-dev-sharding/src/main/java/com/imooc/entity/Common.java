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
@TableName(value = "common")
public class Common {
    private Long commonId;
    private String commonName;
    private String commonDetail;
}
