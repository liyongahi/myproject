package com.mybatisplus.log.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/13
 */
@Data
public class Log {
    private Integer id;
    private String url;
    private Object param;
    private Object result;
    private String ip;
    private String msg;

    private Date startDate;//请求开始时间
    private Date endDate;//请求结束时间

}
