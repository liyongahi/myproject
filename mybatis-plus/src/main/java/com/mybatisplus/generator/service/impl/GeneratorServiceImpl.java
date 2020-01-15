package com.mybatisplus.generator.service.impl;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.common.file.FileUtils;
import com.common.file.IDUtils;
import com.common.resp.util.ResponseResult;
import com.mybatisplus.generator.pojo.GeneratorBean;
import com.mybatisplus.generator.pojo.GeneratorParam;
import com.mybatisplus.generator.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/8
 */
@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorParam generatorParam;

    @Override
    public ResponseResult getCode(GeneratorBean generatorBean) {
        // 代码生成器
        String msg;
        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig globalConfig = getGc(generatorBean);
        log.info(globalConfig.getOutputDir());
        //全局配置
        mpg.setGlobalConfig(globalConfig);

        //数据库配置
        DataSourceConfig dsc = new DataSourceConfig();

        msg = setDataSourceConfig(dsc, generatorBean);
        if (msg != null) {
            return ResponseResult.ERROR(msg);
        }
        mpg.setDataSource(dsc);

        // 包配置
        mpg.setPackageInfo(getPackageConfig(generatorBean));
        //自定义 配置
        mpg.setCfg(getInjectionConfig(generatorBean));


        //模板设置
        mpg.setTemplate(getTemplateConfig());

        //策略配置
        mpg.setStrategy(getStrategyConfig(generatorBean));

        //执行
        try {
            mpg.execute();
            List<TableInfo> tableInfoList = mpg.getConfig().getTableInfoList();
            List<String> tableNameList = new ArrayList<>();
            //数组转集合
            String[] tableList = generatorBean.getTableName();
            for (int i = 0; i < tableList.length; i++) {
                tableNameList.add(tableList[i]);
            }

            if (tableInfoList.size() == tableList.length) {
                tableNameList.removeAll(Arrays.asList(tableList));
            } else {
                //循环剔除已完成的表
                for (String tableName : tableList) {
                    for (TableInfo tableInfo : tableInfoList) {
                        if (tableInfo.getName().equals(tableName)) {
                            tableNameList.remove(tableName);
                        }
                    }
                }
            }
            if (tableNameList.size() > 0) {
                msg = "表 " + tableNameList + " 不存在";
                //删除文件
                FileUtils.delFile(globalConfig.getOutputDir());
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "请检查数据库连接参数，用户名，密码是否正确";
        }


        if (StringUtils.isEmpty(msg)) {
            return ResponseResult.OK("代码生成成功", "http:");
        } else {
            return ResponseResult.ERROR(msg);
        }

    }


    /**
     * 策略配置
     *
     * @param generatorBean
     * @return
     */
    public StrategyConfig getStrategyConfig(GeneratorBean generatorBean) {
        //策略配置
        StrategyConfig strategy = new StrategyConfig();

        //NamingStrategy.underline_to_camel 转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
        strategy.setRestControllerStyle(true);//生成 @RestController 控制器
        strategy.setInclude(generatorBean.getTableName());//需要包含的表名，允许正则表达式

        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符
        strategy.setTablePrefix(generatorBean.getPrefix() == null ? "" : generatorBean.getPrefix());//表前缀
        return strategy;
    }


    /**
     * 全局配置
     *
     * @return
     */
    public GlobalConfig getGc(GeneratorBean generatorBean) {


        //全局配置
        //设置代码文件本地-地址
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(generatorParam.getProjectMainPath() + "\\" + IDUtils.getId());
        gc.setAuthor(generatorBean.getAuthor() == null ? "" : generatorBean.getAuthor());//作者
        gc.setOpen(false);//是否打开输出目录
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false

        return gc;
    }


    /**
     * 数据库配置
     * jdbc:mysql://127.0.0.1:3307/mybatis_plus?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong
     *
     * @param generatorBean
     * @return
     */
    public String setDataSourceConfig(DataSourceConfig dsc, GeneratorBean generatorBean) {
//        DataSourceConfig dsc = new DataSourceConfig();

        if (StringUtils.isEmpty(generatorBean.getDataBaseName())) {
            return "请输入数据库名字";
        }

        if (StringUtils.isEmpty(generatorBean.getTableName())) {
            return "请输入表名";
        }


        if (StringUtils.isEmpty(generatorBean.getUserName())) {
            return "请输入用户名";
        }
        if (StringUtils.isEmpty(generatorBean.getPassword())) {
            return "请输入密码";
        }

        StringBuilder url = new StringBuilder();
        url.append(generatorParam.getDataStartUrl());       //-jdbc:mysql://
        if (StringUtils.isEmpty(generatorBean.getDatasourceUrl())) {

            if (StringUtils.isEmpty(generatorBean.getServerIp())) {
                url.append("127.0.0.1");
            } else {
                url.append(generatorBean.getServerIp());
            }                                               //-jdbc:mysql://127.0.0.1

            url.append(":");                                //-jdbc:mysql://127.0.0.1:
            if (generatorBean.getServerPort() == null ||
                    generatorBean.getServerPort().compareTo(0) == 0) {
                //为空
                url.append("3306");
            } else {
                url.append(generatorBean.getServerPort());
            }                                              //-jdbc:mysql://127.0.0.1:3307

            url.append("/").append(generatorBean.getDataBaseName()).append(generatorParam.getDataEndUrl());
            //                                              //-jdbc:mysql://127.0.0.1:3307/mybatis_plus?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong
            dsc.setUrl(url.toString());
        } else {
            dsc.setUrl(generatorBean.getDatasourceUrl());
        }

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(generatorBean.getUserName());
        dsc.setPassword(generatorBean.getPassword());


        String connMsg = null;
        Connection conn = null;
        //测试连接
        try {
            //SQLSyntaxErrorException 无此数据库
            //CommunicationsException IP地址，端口号
            //SQLException 用户名 密码错误

            conn = DriverManager.getConnection(dsc.getUrl(), dsc.getUsername(), dsc.getPassword());


        } catch (Exception e) {
            String simpleName = e.getClass().getSimpleName();
            switch (simpleName) {
                case "SQLException":
                    connMsg = "请检查数据库账号或密码是否正确";
                    break;
                case "SQLSyntaxErrorException":
                    connMsg = "请检查数据库是否存在";
                    break;
                case "CommunicationsException":
                    connMsg = "请检查数据库ip地址和端口号是否正确";
                    break;
                default:
                    break;
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return connMsg;
    }


    /**
     * 包配置
     *
     * @param generatorBean
     */
    public PackageConfig getPackageConfig(GeneratorBean generatorBean) {
        PackageConfig pc = new PackageConfig();

        if (StringUtils.isEmpty(generatorBean.getPackagePath())) {
            //父包直接取模块名字
            if (!StringUtils.isEmpty(generatorBean.getModuleName())) {
                pc.setParent(generatorBean.getModuleName());
            } else {
                pc.setParent("generator");//没有 取默认
            }
        } else {

            pc.setParent(generatorBean.getPackagePath());


            pc.setModuleName(generatorBean.getModuleName() == null ? null : generatorBean.getModuleName());//父包模块名

        }


        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");//设置控制器包名

        return pc;
    }


    /**
     * 自定义 配置
     */
    public InjectionConfig getInjectionConfig(GeneratorBean generatorBean) {
        //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig injectionConfig = new InjectionConfig() {
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parent", "com.mybatisplus.member");
                map.put("version", generatorBean.getVersion() == null ? "1.0" : generatorBean.getVersion());
                map.put("describe", generatorBean.getDescribe() == null ? "" : generatorBean.getDescribe());
                map.put("requestMapping", generatorBean.getModuleName());
                this.setMap(map);

            }
        };
        return injectionConfig;

    }


    /**
     * 模板设置
     * 暂时只提供 Controller 和 Entity 的模板
     */
    public TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig()
                .setController("templates/Controller.java")
                .setEntity("templates/Entity.java");
        return templateConfig;
    }


    public static void main(String[] args) {

        String s = "java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)";

        System.out.println(s.indexOf("using password: YES1"));
    }


}
