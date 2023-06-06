package com.example.onlineshopingmall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.onlineshopingmall.common.R;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RequestMapping("/user/userCenter")
@RestController
public class UserProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations ops;

    //@PostConstruct 是在自动装配后再执行以下方法
    @PostConstruct
    private void init() {
        ops = redisTemplate.opsForValue();
    }

    //获取用户个人信息
    @Cacheable(cacheNames = "userProfile", key = "#userId")
    @GetMapping("/getUserProfile")
    public R<JSONObject> getUserProfile(@RequestParam("userId") String userId) {
        JSONObject jsonObject = new JSONObject();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserId, userId);
        User one = userService.getOne(wrapper);
        jsonObject.put("username", one.getUserName());
        jsonObject.put("phone", one.getPhone());
        jsonObject.put("sex", one.getSex());
        jsonObject.put("age", one.getAge());
        jsonObject.put("hobbys", one.getHobbys());
        jsonObject.put("createTime", one.getCreateTime());


        return R.success(jsonObject);
    }

    //修改用户个人信息
    @CacheEvict(cacheNames = "userProfile", key = "#user.userId")
    @PostMapping("/postUserProfile")
    public R<String> postUserProfile(@RequestBody User user) {

        user.setHobbys(user.getHobbys().trim());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        userService.update(user, queryWrapper);
        return R.success("修改成功！");
    }


}
