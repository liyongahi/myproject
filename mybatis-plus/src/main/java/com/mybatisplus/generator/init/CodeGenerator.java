package com.mybatisplus.generator.init;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;


import java.util.*;

/**
 * @version 0.1
 * @ClassName CodeGenerator
 * @desc
 * @Date 2019-09-05
 * @Author honggui
 **/
//@Slf4j
public class CodeGenerator {
    //获取项目的路径
    public static String projectPath = System.getProperty("user.dir");
    public static String projectJavaPath = projectPath + "\\mybatis-plus\\generatorFile";


    public static void main(String[] args) {

        String path = CodeGenerator.class.getResource("/").getPath();

        String resourcesPath = path.substring(1, path.indexOf("/target")) + "/src/main/java";

        System.out.println(projectJavaPath);
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();


        //全局配置
        GlobalConfig gc = new GlobalConfig();

        //设置代码文件地址 /src/main/java
        gc.setOutputDir(projectJavaPath);

        gc.setAuthor("");//作者
        gc.setOpen(false);//是否打开输出目录
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        mpg.setGlobalConfig(gc);


        //数据库配置
        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setUrl("jdbc:mysql://127.0.0.1:3307/hellogt?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
//        try {
//            //SQLSyntaxErrorException 无此数据库
//            //CommunicationsException IP地址，端口号
//            //SQLException 用户名 密码错误
//
//            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/hellogt?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong", "root1", "root");
//
//            if (conn != null) conn.close();
//        } catch (Exception e) {
//            System.out.println(e.getClass().getSimpleName());
//            e.printStackTrace();
//        }

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();

        pc.setParent("kh");//父包名。// 自定义包路径  如果为空，将下面子包名必须写全部， 否则就只需写子包名
        //        pc.setModuleName("kh");//父包模块名
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");//设置控制器包名
        mpg.setPackageInfo(pc);


        // 自定义 配置     bizChName;   //业务名称
        // bizEnName; //业务英文名称
        //  bizEnBigName;//业务英文名称(大写)
        InjectionConfig injectionConfig = new InjectionConfig() {
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parent", "com.mybatisplus.member");
                map.put("bizEnName", NamingStrategy.underlineToCamel("kh_user"));
                map.put("version", "0.1");
                map.put("describe", "描述");
                map.put("requestMapping", "kh");
                this.setMap(map);
            }
        };
        mpg.setCfg(injectionConfig);
        /*
        // 自定义配置

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        **/
        // set freemarker engine
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.setTemplate(new TemplateConfig().setXml(null));

        TemplateConfig templateConfig = new TemplateConfig()
                .setController("templates/Controller.java")
                .setEntity("templates/Entity.java");
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();

        //NamingStrategy.underline_to_camel 转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
        strategy.setRestControllerStyle(true);//生成 @RestController 控制器
        String[] tableList = {"kh_user","kh_order"};//需要生成的表数组类型
        strategy.setInclude(tableList);//需要包含的表名，允许正则表达式

        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符
        strategy.setTablePrefix("kh");//表前缀
        mpg.setStrategy(strategy);


//
//        try {
////            Connection conn = DriverManager.getConnection(url, username, password);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//            System.out.println("请检查数据库连接参数，用户名，密码是否正确");
//
//        }

//        ConfigBuilder configBuilder = new ConfigBuilder();
        mpg.execute();



    }

    /**
     * 方法策略 首字母小写
     *
     * @param name
     * @return
     */
    public static String underlineToCamel(String name) {
        // 快速检查
        if (StringUtils.isEmpty(name)) {
            // 没必要转换
            return StringPool.EMPTY;
        }
        String tempName = name;
        // 大写数字下划线组成转为小写 , 允许混合模式转为小写
        if (StringUtils.isCapitalMode(name) || StringUtils.isMixedMode(name)) {
            tempName = name.toLowerCase();
        }
        StringBuilder result = new StringBuilder();
        // 用下划线将原始字符串分割
        String[] camels = tempName.split(ConstVal.UNDERLINE);
        // 跳过原始字符串中开头、结尾的下换线或双重下划线
        // 处理真正的驼峰片段
        Arrays.stream(camels).filter(camel -> !StringUtils.isEmpty(camel)).forEach(camel -> {
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel);
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(capitalFirst(camel));
            }
        });
        return result.toString();
    }

    public static String capitalFirst(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return StringPool.EMPTY;
    }
}
