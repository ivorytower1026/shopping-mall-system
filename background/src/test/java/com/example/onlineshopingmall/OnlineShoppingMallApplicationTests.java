package com.example.onlineshopingmall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.UserService;
import com.example.onlineshopingmall.utils.CreateToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootTest
class OnlineShoppingMallApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations ops;
    @PostConstruct
    private void init(){
        ops = redisTemplate.opsForValue();
    }
    @DisplayName("测试自动注入下redisTemplate的ops方法")
    @Test
    void testRedisTemplate(){
        System.out.println("ops"+ops);
    }



    @Test
    void contextLoads() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAge,3);
        User one = userService.getOne(queryWrapper);
        System.out.println(one);
    }

    @Test
    void testAddUser(){
        User user = new User();
        user.setPhone("1541564544");
        userService.save(user);
    }
    @DisplayName("测试CreateToken")
    @Test
    void testCreateToken(){
        String token = CreateToken.token("13036729653");
        System.out.println(token);
    }

    @DisplayName("测试redis")
    @Test
    void testRedis(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("a:rr","kkkk");
        Object hh = ops.get("a:rr");
        System.out.println(hh);
    }
    @DisplayName("测试service的update方法")
    @Test
    void testUpdate(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,"13988856696");
        User user = new User();
        user.setPhone("11111111111");
        user.setUserName("天喜");
        userService.update(user,queryWrapper );


    }

    @DisplayName("测试JSON中的一些方法")
    @Test
    void testJSON(){
        String s [] = {"1","2"};
        String s1 = JSON.toJSONString(s);
        Object parse = JSONArray.parse(s1);
        System.out.println(s1);

        System.out.println(parse);
    }
    @DisplayName("生成一个随机字符串")
    @Test
    void testString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<random.nextInt(5)+20;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        System.out.println("字符串为"+sb.toString());
    }
    @DisplayName("测试路径")
    void testUrl(){

    }
}
