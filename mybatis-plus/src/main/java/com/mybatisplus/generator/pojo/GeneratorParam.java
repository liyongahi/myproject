package com.mybatisplus.generator.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/8
 */
@Data
@Component
@ConfigurationProperties("generator")
public class GeneratorParam {
    private String projectMainPath;//
    private String dataStartUrl;//
    private String dataEndUrl;//
}
