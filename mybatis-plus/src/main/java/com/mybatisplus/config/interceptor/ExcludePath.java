package com.mybatisplus.config.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/13
 */

@Data
@Component
@ConfigurationProperties("interceptor.exclude")
public class ExcludePath {

    private List<String> path;
}
