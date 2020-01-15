package com.mybatisplus.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/13
 */
@Component
public class MyMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private ExcludePath excludePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new NotLoginInterceptor()).addPathPatterns(excludePath.getPath());
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath.getPath());

    }


}
