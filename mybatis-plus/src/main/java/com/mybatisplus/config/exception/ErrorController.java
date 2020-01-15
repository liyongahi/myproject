package com.mybatisplus.config.exception;

import com.common.resp.util.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/13
 */
@RestController
@RequestMapping("error")
public class ErrorController {

    @RequestMapping("{code}")
    public ResponseResult returnError(@PathVariable Integer code) {
        ResponseResult result = null;
        switch (code) {
            case 302:
                result = ResponseResult.LOGIN_ERROR();
                break;
            default:
                break;
        }
        return result;
    }
}
