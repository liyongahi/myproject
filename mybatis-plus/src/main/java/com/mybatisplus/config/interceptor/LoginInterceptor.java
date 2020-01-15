package com.mybatisplus.config.interceptor;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.common.date.DateUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author LiYonghai
 * @version 1.0
 * @describe 需要登录后才能访问接口的拦截器
 * @date 2020/1/9
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {

        String token = request.getHeader("token");
        /**
         * 登录验证
         */
        if (StringUtils.isEmpty(token)) {
            //返回登录
            response.sendRedirect(request.getContextPath() + "/error/302");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {


    }


}
