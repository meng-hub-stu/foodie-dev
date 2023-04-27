package com.imooc.controller;

import com.imooc.mapper.StuMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author Mengdexin
 * @date 2021 -11 -06 -19:55
 */
public class Mybatis {

    public static void main(String[] args) throws Exception {
        // 1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 2. 创建SqlSessionFactory对象实际创建的是DefaultSqlSessionFactory对象
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(inputStream);
        // 3. 创建SqlSession对象实际创建的是DefaultSqlSession对象
        SqlSession sqlSession = builder.openSession();
        // 4. 创建代理对象
        StuMapper mapper = sqlSession.getMapper(StuMapper.class);
        // 5. 执行查询语句
//        List<User> users = mapper.selectAll();
    }
}
