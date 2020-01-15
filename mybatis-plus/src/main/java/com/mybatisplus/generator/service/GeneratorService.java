package com.mybatisplus.generator.service;

import com.common.resp.util.ResponseResult;
import com.mybatisplus.generator.pojo.GeneratorBean;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/8
 */
public interface GeneratorService {
    ResponseResult getCode(GeneratorBean generatorBean);

}
