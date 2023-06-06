package com.example.onlineshopingmall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.onlineshopingmall.common.R;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.UserService;
import com.example.onlineshopingmall.utils.CreateToken;
import com.example.onlineshopingmall.utils.MD5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequestMapping("/user")
@RestController

public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations ops;
    //@PostConstruct 是在自动装配后再执行以下方法
    @PostConstruct
    private void init(){
        ops = redisTemplate.opsForValue();
    }
    //用户登录映射方法
    @PostMapping("/userlogin")
    public R<JSONObject> userLogin(
            @RequestParam("phone") String phone,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "password", required = false) String password
    ) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User one = userService.getOne(wrapper);
        if (one == null) {
            return R.error("该手机号未注册");
        } else {
            //不可用
            if(one.getIsUsable()==0){
                return R.error("账号不可用，请联系管理员");
            }
            //密码登录
            if (password!=null) {
                String s = MD5Encrypt.encryptToMD5(password);
                String userPass = one.getPassword();
                if (s.equals(userPass)) {
                    //在redis中存入phone和token
                    return getJsonObjectR(phone,"登录成功");
                } else {
                   return R.error("账号或密码错误");
                }
            }
            //验证码登录
            if(checkPhoneCode(phone,code)){
                return getJsonObjectR(phone,"登录成功");
            }else {
                return R.error("手机验证码错误");
            }
        }

    }
    //判断验证码是否正确
    private boolean checkPhoneCode(String phone,String code){
        return Objects.equals(code,(String)ops.get("phoneCode:phone" + phone));
    }
    //生成token 并保存在redis，也返回给前端
    private R<JSONObject> getJsonObjectR(@RequestParam("phone") String phone,String message) {
        JSONObject jsonObject = new JSONObject();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user = userService.getOne(queryWrapper);
        jsonObject.put("userId",user.getUserId());
        String token = CreateToken.token(phone);
        ops.set("userToken:user_id"+user.getUserId(),token,3L, TimeUnit.HOURS );
        jsonObject.put("token",token);
        jsonObject.put("message",message);
        return R.success(jsonObject);
    }
}
