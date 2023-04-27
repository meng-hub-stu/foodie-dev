package com.imooc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.entity.Common;
import com.imooc.entity.StuUser;
import com.imooc.entity.User;
import com.imooc.entity.UserDetail;
import com.imooc.mapper.CommonMapper;
import com.imooc.mapper.UserDetailMapper;
import com.imooc.mapper.UserMapper;
import com.imooc.service.IStuUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Mengdl
 * @date 2022/06/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardTest {

    @Autowired
    private IStuUserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private CommonMapper commonMapper;

    @Test
    public void testMybatisPlus (){
        StuUser stuUser = new StuUser();
        stuUser.setUserName("孟德鑫");
        boolean b = userService.saveOrUpdate(stuUser);
        System.out.println(b);
    }

    @Test
    public void testSharding(){
        for (int i =0;i<100;i++){
            User user = new User();
            user.setName("yue");
            user.setUserId(i+100);
//            hintRule();
            userMapper.insert(user);
        }
    }

    @Test
    public void search(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",100);
//        wrapper.eq("user_id",103);
        wrapper.in("user_id", 109, 111, 161, 165, 156, 158, 132, 134);
        wrapper.orderByDesc("user_id");
        List<User> list = userMapper.selectList(wrapper);
        for (User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void testUserSharding(){
        for (int i =0; i<100; i++){
            UserDetail userDetail = new UserDetail();
            userDetail.setAge("1" + i);
            userDetail.setSex("1");
            userDetailMapper.insert(userDetail);
        }
    }

    @Test
    public void testCommonSharding(){
        for (int i =0;i<100;i++){
            Common common = new Common();
            common.setCommonName("1" + i);
            common.setCommonDetail("1");
            commonMapper.insert(common);
        }
    }

    @Test
    public void searchCommon(){
        QueryWrapper<Common> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",100);
//        wrapper.eq("id",1534866614175653890L);
        wrapper.orderByDesc("common_id");
        List<Common> commons = commonMapper.selectList(wrapper);
        for (Common user:commons){
            System.out.println(user);
        }
    }

    public void hintRule(){
        // 清除掉上一次的规则，否则会报错
        HintManager.clear();
        // HintManager API 工具类实例
        HintManager hintManager = HintManager.getInstance();
        // 直接指定对应具体的数据库
        hintManager.addDatabaseShardingValue("ds",1);
        // 设置表的分片健
        hintManager.addTableShardingValue("user" , 1);
        hintManager.addTableShardingValue("user" , 2);
        // 在读写分离数据库中，Hint 可以强制读主库
        hintManager.setMasterRouteOnly();
    }

}
