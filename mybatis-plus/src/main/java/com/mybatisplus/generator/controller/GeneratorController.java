package com.mybatisplus.generator.controller;

import com.common.resp.util.ResponseResult;
import com.mybatisplus.generator.pojo.GeneratorBean;
import com.mybatisplus.generator.service.GeneratorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/8
 */
@Api

@RestController
@RequestMapping(value = "generator" ,method = RequestMethod.POST)
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @RequestMapping("getCode")
    public ResponseResult getCode(GeneratorBean generatorBean) {
        return generatorService.getCode(generatorBean);
//        return ResponseResult.OK(msg);
    }

}
