package com.mybatisplus.config.exception;

import com.common.resp.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一处理异常
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult ExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        //系统级异常
        ResponseResult responseResult = ResponseResult.SYS_ERROR();

        /**
         * 空指针异常
         */
        if (e instanceof NullPointerException) {
            log.info("空指针异常");
            e.printStackTrace();
        }

        return responseResult;
    }
}
