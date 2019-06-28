package com.smallflyingleg.sso.utils;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.smallflyingleg.sso.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MBG {


    public static void main(String[] args) {
        // 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("wdx") // 作者
                .setOutputDir("F:\\project\\sso\\src\\main\\java") // 生成路径
                .setEnableCache(false) //缓存  false不开启
                .setFileOverride(true)// 文件覆盖
                .setServiceName("%sService") // 设置生成的service接口名
                .setIdType(IdType.AUTO) // 主键策略
                .setOpen(false)    //不打开生成文件的目录
        ;
        // 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL).setUrl("jdbc:mysql://localhost:3306/small?serverTimezone=GMT").setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root").setPassword("root");
        // 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) // 全局大写命名
                .setDbColumnUnderline(true) // 表名 字段名 是否使用下滑线命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setEntityLombokModel(true)     //lombok模型
                .setRestControllerStyle(true)   //restController
                .setInclude(new String[]{"sf_item", "sf_order_item"}) // 生成的表
//                ,"sf_workflow_event","sf_workflow_event_user","sf_workflow_node","sf_workflow_record"
                .setTablePrefix("sf_"); // 表前缀

        // 包名策略
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.smallflyingleg.sso").setController("controller").setEntity("pojo").setService("service").setXml("mapper");
        AutoGenerator ag = new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setTemplate(
                        new TemplateConfig()
                                //.setXml(null)//指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
                                //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
                                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                                 .setController("templates/controller.java")
                                // .setEntity("...")
                                // .setMapper("...")
                                // .setXml("...")
                                // .setService("...")
                                // .setServiceImpl("templates/serviceImpl.java")
                );

        ag.execute();


    }


}
