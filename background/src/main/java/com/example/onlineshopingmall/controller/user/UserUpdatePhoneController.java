package com.example.onlineshopingmall.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.onlineshopingmall.common.R;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Objects;

@RequestMapping("/user/userCenter")
@RestController
public class UserUpdatePhoneController {
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
    //修改用户手机号
    @CacheEvict(cacheNames = "userProfile",key="#userId")
    @PostMapping("/postUserPhone")
    public R<String> postUserPhone(@RequestParam("phone1")String phone1,
                                   @RequestParam("phone2")String phone2,
                                   @RequestParam("code1")String code1,
                                   @RequestParam("code2")String code2,
                                   @RequestParam("userId")String userId) {

        //如果新手机号已经绑定了，就不能再绑定了
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getPhone, phone2);
        User one1 = userService.getOne(queryWrapper1);
        if (one1 != null) {
            return R.error("该手机号已绑定");
        }

        String s1 = (String) ops.get("phoneCode:phone" + phone1);
        String s2 = (String) ops.get("phoneCode:phone" + phone2);
        if (!Objects.equals(code1, s1) && !Objects.equals(code2, s2)) {
            System.out.println("手机" + phone1 + "验证码是:" + s1);
            System.out.println("手机" + phone2 + "验证码是:" + s2);
            return R.error("手机验证码错误");
        } else {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone1);
            User one = userService.getOne(queryWrapper);
            one.setPhone(phone2);
            userService.update(one, queryWrapper);
            return R.success("修改成功！");
        }
    }
}
