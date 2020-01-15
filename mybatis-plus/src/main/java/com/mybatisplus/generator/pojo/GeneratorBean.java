package com.mybatisplus.generator.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/6
 */
@Data
@ApiModel(value = "GeneratorBean对象", description = "")
public class GeneratorBean {

    @ApiModelProperty(value = "数据库ip地址-本地数据库可以为空")
    private String serverIp;//数据库ip地址，默认本地
    @ApiModelProperty(value = "数据库端口号-本地数据库可以为空", example = "3306")
    private Integer serverPort;//数据库服务端口号默认3306
    @ApiModelProperty(value = "要连接的数据库")
    private String dataBaseName;//数据库
    @ApiModelProperty(value = "数据库全连接")
    private String datasourceUrl;//数据库全连接(不传进行拼接，传直接进行连接) serverIp+servsrPort+dataBaseName+?后缀
    @ApiModelProperty(value = "数据库用户名", required = true)
    private String userName;//数据库用户名
    @ApiModelProperty(value = "数据库密码", required = true)
    private String password;//数据库密码
    @ApiModelProperty(value = "要生成的表-数组类型", required = true)
    private String[] tableName;//表名 数组类型
    @ApiModelProperty(value = "表前缀")
    private String prefix;//表前缀


    @ApiModelProperty(value = "模块名称")
    private String moduleName;//模块名称

//    @ApiModelProperty(value="绝对路径-本地项目使用可以为空",position = 10)
//    private String projectPath;  //项目绝对路径

    @ApiModelProperty(value = "包路径")
    private String packagePath;//包路径
    @ApiModelProperty(value = "作者")
    private String author;//作者

    @ApiModelProperty(value = "版本号")
    private String version;//版本号
    @ApiModelProperty(value = "描述信息")
    private String describe;//描述信息

    @ApiModelProperty(value = "打包下载")
    private boolean isDownload;//是否下载


//    @ApiModelProperty(value="请求路径默认模块/实体类",position = 15,example = "model/user")
//    private String requestMapping;//请求路径 默认模块

//    private boolean isController;//是否生成controller
//
//    private boolean isService;//是否生成service
//
//    private boolean isMapper;//是否生成mapper接口
//
//    private boolean isDownload;//是否打包下载


}
