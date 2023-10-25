# 前言

该项目是springboot和vue2构建的一个web项目，此README文件用于记录一些前后端用到的知识点。

整个web项目包括登录模块，订单模块，购物车模块，个人信息模块，商品信息模块，商户信息模块

使用的技术栈有后端的 springboot , mybatis-plus , redis , springmvc , mysql

前端用到的有 html , css , javascript , vue2 , element-ui

# 后端

技术栈 spring-boot

## 配置文件

### 引入依赖

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>OnlineShopingMall</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>OnlineShopingMall</name>
    <description>OnlineShopingMall</description>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.3.7.RELEASE</spring-boot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
<!--    引入json依赖-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>
<!--        //引入redis 和 cache-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <!--        redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <version>2.3.7.RELEASE</version>
        </dependency>



        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.23</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version>8.0.26</version>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.7.RELEASE</version>
                <configuration>
                    <mainClass>com.example.onlineshopingmall.OnlineShoppingMallApplication</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

### 配置数据库，redis，端口号

application.yml

```yml
server:
  port: 8081
spring:
  application:
    #应用的名称，可选
    name: reggie_take_out
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/shopping_mall?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: root
      password: 123456
  redis:
    database: 0
    host: localhost
    port: 6379
  cache:
    redis:
      time-to-live: 180000000  #以毫秒为单位 这里是ttl 设置为30分钟
```

## 配置拦截器

此处是登录拦截器，用于识别用户是否登录，配合redis使用

### 1.编写拦截器类

- loginInterceptor.java 此类实现了 HandlerInterceptor接口
  
- 主要是重写preHandle方法，若放行就return true ，否则就返回false
  
- 返回之前可以在返回头添加信息，便于前端识别
  

        如：``response.addIntHeader("type", 1);``

- 使用redis获取值

```java
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
public class LoginInterceptor implements  HandlerInterceptor  {
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
        //获取redis操作对象
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
```

### 2.配置到webconfig中

WebMvcConfig.java

- 配置需要拦截的请求路径和放行路径
  
- addPathPatterns 需要拦截的路径
  
- excludePathPatterns 放
  

```java
package com.example.onlineshopingmall.config;

import com.example.onlineshopingmall.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    LoginInterceptor loginInterceptor;
    /**
     * 配置需要拦截的请求路径和放行路径
     * addPathPatterns 需要拦截的路径
     * excludePathPatterns 放行路径
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(
                    "/user/userlogin","/user/usersign","/getPhoneCode",
                    "/merchant/merchantSign","/merchant/merchantLogin",
                    "/administrator/administratorSign","/administrator/administratorLogin"
            );
            super.addInterceptors(registry);
    }
}
```

## 自动添加同一字段数据

我们的实体类都是有一些相同的字段，每次进行修改或添加都要手动编写代码进行添加，十分麻烦，所以可以配置一个类，用于当数据库中某个表中的某一条数据被修改，或增加了一条新的数据时，将自动填充这些字段

### 1.配置CommonField类

CommonField.java

- 实现了MetaObjectHandler类
  
- 重写insertFill方法，当表中新增一条数据时，自动填充字段
  
- 重写updateFill，当表中的某条数据被修改时，自动修改该字段的值
  

```java
package com.example.onlineshopingmall.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonField implements  MetaObjectHandler {

    /**
     * 在插入时自动执行该方法
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //存入key值

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
```

### 2.在实体类中使用@TableField

在实体类中需要自动填充的字段上写个注解

```java
 @TableField(fill = FieldFill.INSERT_UPDATE) 
 private LocalDateTime updateTime; 
```

User.java

```java
package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId
    private String userId;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String phone;

    private String password;

    /**
     * 
     */
    private String userHeadPicName;

    /**
     * 性别 1表示男     2表示女
     */
    private Integer sex;

    /**
     * 
     */
    private Integer age;
    /**
     * 爱好
     */
    private String hobbys;

    /**
     * 
     */
    private Integer isDeleted;
    private Integer isUsable;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE) 
    private LocalDateTime updateTime; 



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
```

## 新增数据时自动生成id

让每次新增的数据自动生成随机id，利用注解``@TableId``即可完成

```java
    @TableId
    private String userId;
```

## 配置分页插件

固定写法

MybatisPlusInterceptor.java

```java
package com.example.onlineshopingmall.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
```

## 分页代码

```java
//用于商品界面获取商品分页信息
    @GetMapping("/getGoodsInfo")
    public R<Page<Goods>> getGoodsInfo(@RequestParam("goodsCategoryId") String goodsCategoryId,
                                       @RequestParam("page") Long page,
                                       @RequestParam(value = "keyword" ,required = false) String keyword,
                                       @RequestParam("pageSize") Long pageSize) {

        //通过页码和页码大小获取分页数据
        Page<Goods> goodsPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        //匹配要分页的数据
        if(keyword!=null&&!keyword.equals("")){
            wrapper.like(Goods::getGoodsName,keyword);
        }else {
            wrapper.eq(Goods::getGoodsCategoryId, goodsCategoryId);
        }
        goodsService.page(goodsPage, wrapper);
        return R.success(goodsPage);
    }
```

## 请求线程存储用户id

场景：很多时候需要用到用户id，进行查询数据，因此可将其存放到当此请求线程中

- （1）从请求头中获取用户id（前提是前端在请求头中添加了用户id字段）
  
- （2）将id存入到当此请求线程中
  
- （3）在当此请求需要用到的地方获取该id
  

### 1.编写线程工具类

BaseContext.java

```java
package com.example.onlineshopingmall.utils;

/**
 * 此类可以将需要用到的值存入到当前请求线程中，可以便于线程后续使用
 */
public class BaseContext {
    //创造一个静态的ThreadLocal对象,并声明泛型类
    private static ThreadLocal<String> threadLocal  = new ThreadLocal<>();
    //存入key值
    public static void setSessionId(String id){
        threadLocal.set(id);
    }
    //获取key值
    public static String getSessionId(){
        return threadLocal.get();
    }
}
```

### 2.存入用户id

在拦截器中就可以存入用户id，从请求头中获取id再存放到此线程中

```java
String userId = request.getHeader("userId");
BaseContext.setSessionId(userId);
```

### 3.获取用户id

在controller中就可以通过当前请求线程获取id

```java
String userId = BaseContext.getSessionId();
```

## 对象映射器

**问题分析**
当服务器将Long型数据传给前端页面时会造成精度缺失,此时就可以利用对象映射器处理该问题,(此处提供一种解决方法,核心就是将long型转为string类型)
**步骤**
1.编写JacksonObjectMapper类

```java
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}
```

2.添加到WebMvcConfig 类中的extendMessageConverters方法中

```java
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 添加我们自己的消息转换器,将我们自己的转换器放在最前面
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将我们自己的转换器放在最前面
        converters.add(0,messageConverter);
    }
}
```

## 上传和下载文件

### 上传文件

```java
 //添加商品图片 
    @PostMapping("/uploadGoodsPic")
    public R<String> uploadGoodsPic(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID() + suffix;
        String path = basePath + merchantGoodsPath;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            //存储上传的文件
            file.transferTo(new File(path + filename));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }
```

### 下载文件

```java
//显示回显图片
    @GetMapping("/downloadGoodsPic")
    public void download(HttpServletResponse response, String name) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + merchantGoodsPath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            //设置文件格式
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

## copyProperties拷贝对象

```java
@GetMapping("/getMerchantGoodsInfo")
    public R<Page> getMerchantGoodsInfo(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        String merchantId = BaseContext.getSessionId();
        Page<Goods> goodsPage = new Page<>(page, pageSize);
        Page<GoodsDto> goodsDtoPage = new Page<>();
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getMerchantId, merchantId);
        queryWrapper.orderByDesc(Goods::getCreateTime);
        goodsService.page(goodsPage, queryWrapper);

        //拷贝分页数据，除了records数据，只要一些基本的分页信息，如页码，页大小
        BeanUtils.copyProperties(goodsPage, goodsDtoPage, "records");

        LambdaQueryWrapper<GoodsCategory> goodsCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Goods> records = goodsPage.getRecords();
        List<GoodsDto> collect = records.stream().map((item) -> {
            GoodsDto goodsDto = new GoodsDto(); 
            //想要在原本的实体类中添加新的字段，将原本的实体类拷贝到dto对象中
            BeanUtils.copyProperties(item, goodsDto);

            goodsCategoryLambdaQueryWrapper.eq(GoodsCategory::getGoodsCategoryId, item.getGoodsCategoryId());
            GoodsCategory one = goodsCategoryService.getOne(goodsCategoryLambdaQueryWrapper);
            String goodsCategoryName = one.getGoodsCategoryName();
            goodsDto.setGoodsCategoryName(goodsCategoryName);
            goodsCategoryLambdaQueryWrapper.clear();
            return goodsDto;
        }).collect(Collectors.toList());

        goodsDtoPage.setRecords(collect);
        return R.success(goodsDtoPage);
    }
```

## md5加密

```java
package com.example.onlineshopingmall.utils;

import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class MD5Encrypt {
    public static String encryptToMD5(String str) {
       String s1 = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
       return encrypt(s1);
    }

    /**
     * 修改md5密码
     * @param str
     * @return
     */
    private static String encrypt(String str){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int i1 = str.charAt(i);
            String s = i1+"e";
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
```

# 前端

技术栈 vue

## 准备工作

### 1.配置main.js文件

引入ElementUI，VueRouter

main.js

```js
import Vue from 'vue'
import App from './App.vue'
import store from "./store/index"
Vue.config.productionTip = false
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router';
import router from "./router/index.js"

Vue.use(ElementUI);
Vue.use(VueRouter)
const vm = new Vue({
  el: "#app",
  render: h => h(App),
  store,
  router: router,
  beforeCreate() {
    Vue.prototype.$bus = this
  }

})
export default vm
```

### 2.配置vue.config.js

配置端口号，代理

vue.config.js

```js
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,

  devServer: {
    port: 8080,
    proxy: {
      '/api': {

        target: "http://localhost:8081",
        // ws: true,
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        },
      }
    }
  }
})
```

### 3.request请求

- 创建request目录，并在该目录下编写index.js
  
- 安装axios
  

index.js

```js
import axios from 'axios'
import store from "@/store/index"
import vm from "@/main"

export function request(config) {
    // 1.创建axios的实例
    const instance = axios.create({
        // 设置基础的url配置项，这样接口处的url前面就不用写url:'http://127.0.0.1:8000/api/home'，直接写成 url:'/api/home', 就可以了
        baseURL: '/api',
        //设置请求超时时间
        timeout: 5000
    })

    // 2.axios的拦截器，用不到的可以忽略这节
    // 2.1.请求拦截的作用
    instance.interceptors.request.use(config => {

        let request_url = config.url.split("/")[1]
        if (request_url === "user") {
            config.headers.userId = localStorage.getItem("user_id")
            config.headers.token = localStorage.getItem("user_token")
        }
        else if (request_url === "administrator") {
            config.headers.administratorId = localStorage.getItem("administrator_id")
            config.headers.administratorToken = localStorage.getItem("administrator_token")
        }
        else if (request_url === "merchant") {
            config.headers.merchantId = localStorage.getItem("merchant_id")
            config.headers.merchantToken = localStorage.getItem("merchant_token")
        }

        return config
    }, err => {
        console.log('请求拦截err: ' + err);
    })

    // 2.2.响应拦截
    instance.interceptors.response.use(res => {
        //通过type来判断是哪个响应 1~用户 2~商户 3~管理员
        var type = res.headers.type
        if (type === "1") {

            store.commit("set_isLogin", false);
            store.commit("set_loginSuccess", true);

            //用户
        }
        else if (type === '2') {

            //商户
        }
        else if (type === '3') {
            //管理员
        }
        return res.data
    }, err => {
        var type = err.response.headers.type
        console.log(err);
        console.log(err.response.headers.type);

        if (type == '1') {
            store.commit("set_isLogin", true)
            store.commit("set_loginSuccess", false);
        }
        //用户

        if (type === '2') {
            vm.$router.push({
                path: "/Login",
                query: {
                    typeId: type,
                },
            })
            vm.$message.info("请先登录")
            //商户
        }
        else if (type === '3') {
            //管理员
        }
        console.log('响应拦截err: ' + err);
    })

    // 3.发送真正的网络请求
    return instance(config)
}
```

### 4.router路由配置

```js
import VueRouter from 'vue-router'

const router = new VueRouter({

    routes: [
        //用户相关操作
        {
            path: "/user",
            component: () => import("@/components/user/userDisplay.vue"),

            redirect: "/user/ShoppingMall",
            children: [
                //根据具体的商品id搜寻跳转到的新页面
                {
                    path: "GoodsDetailed",
                    component: () => import("@/pages/user/goodsDetailed/GoodsDetailed.vue"),
                    meta: {
                        title: "商品详情页"
                    }
                },
                {
                    path: "ShoppingMall",
                    query: {
                        keyWord: ""
                    },
                    component: () => import("@/pages/user/ShoppingMall.vue"),
                    meta: { title: "商城中心" },
                },
                {
                    path: "UserCart",
                    component: () => import("@/pages/user/UserCart.vue"),
                    meta: {
                        loginAuth: true,
                        title: "购物车"
                    }
                },
                {
                    path: "UserOrder",
                    component: () => import("@/pages/user/UserOrder.vue"),
                    meta: {
                        loginAuth: true,
                        title: "订单"
                    }
                },
            ],
        },
    ]
})

// router.beforeEach((to, from, next) => {
//     if (to.meta.loginAuth === true) {
//         if (store.state.loginSuccess === true) {
//             next()
//         }
//     } else {
//         next()
//     }
// })


router.afterEach((to, from) => {
    document.title = to.meta.title || "网上商城系统";
})

export default router
```

### 5.App.vue

添加 <router-view></router-view>

```html
<template>
  <div>
    <router-view></router-view>  
  </div>
</template>

<script>
export default {
  name: "APP",

  created() {
  },
};
</script>

<style >

</style>
```

## 使用request

新建api目录，存放各种请求的目录，并新建index.js

index.js

```js
//获取商品用
import { request } from "@/request/request"

//获取商品信息
export function getGoodsInfo(params) {
    return request({
        url: '/goods/getGoodsInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
```

## 路由跳转

-  this.$router.push
  
  ```js
   this.$router.push({
        path: "/user/ShoppingMall",
        //向query中的keyword存入值，取值：
        query:{
            keyword:this.keyword
        }
   });
  ```
  
-  ElementUI中<el-menu-item>中的index属性设置路径
  
  ```html
   <el-menu-item
          index="/user/UserCenter"
          style="float: right; margin-right: 50px">
   个人中心</el-menu-item>
  ```
