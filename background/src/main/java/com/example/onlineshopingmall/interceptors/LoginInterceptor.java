package com.example.onlineshopingmall.interceptors;

import com.example.onlineshopingmall.common.BaseContext;
import com.example.onlineshopingmall.entity.domain.Merchant;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.MerchantService;
import com.example.onlineshopingmall.entity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    MerchantService merchantService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //获取请求的基地址如 user merchant administrator
        String[] split = requestURI.split("/");
        ValueOperations ops = redisTemplate.opsForValue();
        String tpyeStr = split[1];

        //匹配到user路径
        if (tpyeStr.equals("user")) {
            String userId = request.getHeader("userId");
            BaseContext.setSessionId(userId);
            String token = request.getHeader("token");
            String getToken = (String) ops.get("userToken:user_id" + userId);
            response.addIntHeader("type", 1);
            //判断是否可用
            User user = userService.getById(userId);
            if(user.getIsUsable()==0){
                response.addIntHeader("typecode",0);
                response.setStatus(401);
                System.out.println("该用户已禁用");
                return false;
            }
            return tokenValid(response, token, getToken);
        }
        //匹配到merchant
        else if (tpyeStr.equals("merchant")) {
            String merchantId = request.getHeader("merchantId");
            BaseContext.setSessionId(merchantId);
            String token = request.getHeader("merchantToken");
            String getToken = (String) ops.get("merchantToken:merchant_id" + merchantId);
            response.addIntHeader("type", 2);
            //判断是否可用
            Merchant merchant = merchantService.getById(merchantId);
            if(merchant.getIsUsable()==0){
                response.addIntHeader("typecode",0);
                response.setStatus(401);
                System.out.println("该商户已禁用");
                return false;
            }
            return tokenValid(response, token, getToken);
        }
        //匹配到administrator
        else if (tpyeStr.equals("administrator")) {
            String administratorId = request.getHeader("administratorId");
            BaseContext.setSessionId(administratorId);
            String token = request.getHeader("administratorToken");
            String getToken = (String) ops.get("administratorToken:administrator_id" + administratorId);
            response.addIntHeader("type", 3);
            return tokenValid(response, token, getToken);
        }
        return true;
    }



    //判断token是否已失效
    private boolean tokenValid(HttpServletResponse response, String token, String getToken) {
        if (Objects.equals(token, getToken)) {
            response.addIntHeader("typecode",1);
            return true;
        } else {
            response.addIntHeader("typecode",0);
            response.setStatus(401);
            System.out.println("token失效");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
