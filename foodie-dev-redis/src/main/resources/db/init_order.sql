-- 订单表
CREATE TABLE `order1` (
    `id` bigint(64) unsigned NOT NULL COMMENT '主键',
    `total_price` decimal (10,2) default null comment '总价',
    `address` varchar (255) default null comment '地址',
    `status` tinyint(1) DEFAULT '1' COMMENT '状态',
    `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
    `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
-- 订单详情表
CREATE TABLE `order_item` (
    `id` bigint(64) unsigned NOT NULL COMMENT '主键',
    `order_id` bigint(64) unsigned NOT NULL COMMENT '订单',
    `price` decimal (10,2) default null comment '价格',
    `count` int(4) default  null comment '数量',
    `product_id` bigint(64) unsigned NOT NULL COMMENT '产品的id',
    `status` tinyint(1) DEFAULT '1' COMMENT '状态',
    `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
    `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';
-- 产品表
CREATE TABLE `product` (
     `id` bigint(64) unsigned NOT NULL COMMENT '主键',
     `price` decimal (10,2) default null comment '价格',
     `count` int(4) default  null comment '数量',
     `status` tinyint(1) DEFAULT '1' COMMENT '状态',
     `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
     `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';