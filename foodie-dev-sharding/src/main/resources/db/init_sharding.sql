-- 分表user1，存在两个数据库
create table user1(
                      id BIGINT(20) PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      user_id BIGINT(20) NOT NULL
);
-- 分表user2，存在两个数据库
create table user2(
                      id BIGINT(20) PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      user_id BIGINT(20) NOT NULL
);
-- 垂直分表，只存在一个数据库上
CREATE TABLE user_detail(
                            user_id BIGINT(20) PRIMARY KEY,
                            age VARCHAR(50) NOT NULL,
                            sex VARCHAR(2) NOT NULL
);
-- 广播表，所有的数据库都存在
create table common(
                       common_id BIGINT(20) PRIMARY KEY,
                       common_name VARCHAR(50) NOT NULL,
                       common_detail VARCHAR(20) NOT NULL
);

