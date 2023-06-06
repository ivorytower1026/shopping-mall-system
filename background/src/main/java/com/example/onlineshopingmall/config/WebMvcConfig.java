package com.example.onlineshopingmall.config;


import com.example.onlineshopingmall.common.JacksonObjectMapper;
import com.example.onlineshopingmall.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(
                    "/user/userlogin","/user/usersign","/getPhoneCode",
                    "/merchant/merchantSign","/merchant/merchantLogin",
                    "/administrator/administratorSign","/administrator/administratorLogin"
            );
            super.addInterceptors(registry);
    }

    /**
     * 添加我们自己的消息转换器,将我们自己的转换器放在最前面
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0,messageConverter);
    }
}

