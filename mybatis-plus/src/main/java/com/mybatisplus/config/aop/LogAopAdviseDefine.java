package com.mybatisplus.config.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mybatisplus.log.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/13
 */

@Component
@Aspect
@Slf4j
public class LogAopAdviseDefine {

    @Autowired
    private RedisTemplate redisTemplate;

    // 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
    //@Pointcut("within(com.blue.module.service.UserService)")
    //
    @Pointcut("execution(* com.mybatisplus.*.controller.*.*(*))||execution(* com.mybatisplus.*.service.impl.*.*(*))")
    public void pointcut() {
    }

    // 定义 advise
    @Before("pointcut()")
    public void logMethodInvokeParam(JoinPoint point) {
        try {
            Log log1 = new Log();

            redisTemplate.opsForValue().set("1", log1);

            log.info("Before method {} invoke, param: {}", point.getSignature().toShortString(), point.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 整个方法结束后执行
     *
     * @param point
     * @param retVal
     */
    @AfterReturning(pointcut = "pointcut()", returning = "retVal")
    public void logMethodInvokeResult(JoinPoint point, Object retVal) {

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURL().toString();
            log.info("请求接口地址{}", url);
            log.info("参数:{}", JSON.toJSONString(request.getParameterMap()));
            log.info("执行方法 {}, 返回结果: {}", point.getSignature().toShortString(), retVal.toString());

            log.info("最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);

        } catch (Exception e) {
            /**
             * 当aop出现异常时不影响请求的正常返回
             */
            e.printStackTrace();
        }
    }

    /**
     * 出现异常时执行
     *
     * @param point
     * @param exception
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    public void logMethodInvokeException(JoinPoint point, Exception exception) {

        log.info("method {} invoke exception: {}", point.getSignature().toShortString(), exception.toString());
    }


}
