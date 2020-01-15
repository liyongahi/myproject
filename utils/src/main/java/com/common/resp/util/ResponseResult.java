package com.common.resp.util;

import lombok.Data;

@Data
public class ResponseResult {

    private int code;
    private String msg;
    private Object data;


    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //无参
    public static ResponseResult OK() {
        return new ResponseResult(ResponseResultMessage.SUCCESS_CODE, ResponseResultMessage.SUCCESS_MSG, null);
    }

    public static ResponseResult OK(Integer code) {
        return new ResponseResult(code, ResponseResultMessage.SUCCESS_MSG, null);
    }

    public static ResponseResult OK(Object data) {
        return new ResponseResult(ResponseResultMessage.SUCCESS_CODE, ResponseResultMessage.SUCCESS_MSG, data);
    }


    public static ResponseResult OK(String msg) {
        return new ResponseResult(ResponseResultMessage.SUCCESS_CODE, msg, null);
    }

    public static ResponseResult OK(Integer code, String msg) {
        return new ResponseResult(code, msg, null);
    }

    public static ResponseResult OK(Integer code, Object data) {
        return new ResponseResult(code, ResponseResultMessage.SUCCESS_MSG, data);
    }

    public static ResponseResult OK(String msg, Object data) {
        return new ResponseResult(ResponseResultMessage.SUCCESS_CODE, msg, data);
    }

    public ResponseResult OK(Integer code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }


    public static ResponseResult ERROR() {
        return new ResponseResult(ResponseResultMessage.ERROR_CODE, ResponseResultMessage.ERROR_MSG);
    }

    public static ResponseResult ERROR(Integer code) {
        return new ResponseResult(code, ResponseResultMessage.ERROR_MSG);
    }

    public static ResponseResult ERROR(String msg) {
        return new ResponseResult(ResponseResultMessage.ERROR_CODE, msg);
    }

    public static ResponseResult ERROR(Integer code, String msg) {
        return new ResponseResult(code, msg);
    }


    /**
     * 数据为空
     *
     * @return
     */
    public static ResponseResult NULL_DATA() {
        return new ResponseResult(ResponseResultMessage.ERROR_CODE, "暂无数据");
    }


    /**
     * 参数错误
     *
     * @return
     */
    public static ResponseResult PARAM_ERROR() {
        return new ResponseResult(ResponseResultMessage.ERROR_CODE, "参数错误");
    }


    public static ResponseResult SYS_ERROR() {
        return new ResponseResult(ResponseResultMessage.ERROR_CODE, ResponseResultMessage.SYS_ERROR);
    }


    public static ResponseResult LOGIN_ERROR() {
        return new ResponseResult(ResponseResultMessage.LOGIN_ERROR_CODE, ResponseResultMessage.LOGIN_ERROR);

    }

}
