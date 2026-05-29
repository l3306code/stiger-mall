package com.besrey.minio.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.besrey.minio.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;


    @Test
    void testInsert() {
        User user = new User();
        user.setId(3L);
        user.setNickName("hqs");
        user.setUsername("hqs");
        user.setPassword("123456");
        user.setSex(1);
        userMapper.insert(user);
    }


    @Test
    void testSelectById(){
        User user = userMapper.selectById(1L);

        System.out.println("user = " + user);
    }


    @Test
    void testQueryWrapper(){
        // 条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "sex", "email", "update_time")
                .like("username", "j");


        //查询
        List<User> users = userMapper.selectList(wrapper);

        users.forEach(System.out::println);

    }

    @Test
    void testUpdateByQueryWrapper(){
        User user = new User();
        user.setEmail("lwj123@stiger.com");

        // 更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "lwj");

        userMapper.update(user, wrapper);

    }



}
