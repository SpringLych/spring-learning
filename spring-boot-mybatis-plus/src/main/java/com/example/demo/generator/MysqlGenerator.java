package com.example.demo.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiYingChun
 * @date 2019/8/7
 */
public class MysqlGenerator {

    private static final String PACKAGE_NAME = "com.example";

    private static final String OUT_PATH = "/src/main/java";

    private static final String AUTHOR = "spring";

    private static final String MODULE_NAME = "demo";

    /**
     * 数据库相关连接
     */
    private static final String URL = "jdbc:mysql://:3306/springboot_mybatis?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";

    private static final String USERNAME = "";

    private static final String PASSWORD = "";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        String projectPath = System.getProperty("user.dir");
        generator.setGlobalConfig(new GlobalConfig()
                // 输出目录
                .setOutputDir(projectPath + OUT_PATH)
                // 覆盖文件
                .setFileOverride(true)
                .setOpen(false)
                .setAuthor(AUTHOR)
                .setXmlName("%sMapper")
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sDao")
                .setServiceName("%Service")
                // 开启resultMap
                .setBaseResultMap(true)
                // 开启BaseColumnList
                .setBaseColumnList(true)
        );

        // 数据源配置
        generator.setDataSource(new DataSourceConfig()
                .setUrl(URL)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setDriverName(DRIVER)
        );

        //包配置
        generator.setPackageInfo(new PackageConfig()
                .setModuleName(MODULE_NAME)
                .setParent(PACKAGE_NAME)
                .setMapper("dao")
        );


        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 配置xxxMapper.xml路径和文件名
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置模板
        generator.setTemplate(new TemplateConfig()
                .setXml(null)
        );

        // 策略配置
        generator.setStrategy(new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // public User setName(String name) {this.name = name; return this;}
                .setEntityColumnConstant(true)
                .setEntityLombokModel(true)
                .setControllerMappingHyphenStyle(true)
        );

        generator.setTemplateEngine(new VelocityTemplateEngine());

        // 执行
        generator.execute();
    }
}
