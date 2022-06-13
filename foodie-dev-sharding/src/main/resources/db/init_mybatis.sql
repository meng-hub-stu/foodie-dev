-- 测试mybatis-plus的用户表
CREATE TABLE `stu_user` (
                            `id` bigint(64) NOT NULL COMMENT '主键',
                            `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表'