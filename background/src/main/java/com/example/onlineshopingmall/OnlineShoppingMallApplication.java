package com.example.onlineshopingmall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
@EnableCaching

public class OnlineShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingMallApplication.class, args);
    }

}
