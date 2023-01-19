package com.css.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatis-plus 代码生成器
 * Created by jiming.jing on 2018/1/3.
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("请输入" + tip + "：");
        System.out.println(sb.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 全局配置
     *
     * @return
     */
    public static GlobalConfig initGlobal(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("jiming.jing");
        globalConfig.setOpen(false);
        // 重新生成时文件是否覆盖
        globalConfig.setFileOverride(false);
        // 实体属性 Swagger2 注解
        globalConfig.setSwagger2(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        // 去掉Service接口的首字母I
        globalConfig.setServiceName("%sService");
        return globalConfig;
    }

    /**
     * 数据源配置
     *
     * @return
     */
    public static DataSourceConfig initDataSource() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:dm://10.13.102.198:5236/m_db_intelli_tool?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        //dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDbType(DbType.DM);
        dataSourceConfig.setDriverName("dm.jdbc.driver.DmDriver");
        dataSourceConfig.setUsername("SYSDBA");
        dataSourceConfig.setPassword("SYSDBA");
        return dataSourceConfig;
    }

    /**
     * 包配置
     *
     * @return
     */
    public static PackageConfig initPackage(String projectPath) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.css.intelli");
        //packageConfig.setModuleName("xm");
        // TODO
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        // 自定义文件输出目录，适用多Module工程，单体工程不需要此配置
        HashMap<String, String> pathMap = new HashMap<>();
        pathMap.put(ConstVal.ENTITY, projectPath + "/rd-intelli-model/src/main/java/com/css/intelli/entity/");
        pathMap.put(ConstVal.SERVICE, projectPath + "/rd-intelli-service/src/main/java/com/css/intelli/service/");
        pathMap.put(ConstVal.MAPPER, projectPath + "/rd-intelli-service/src/main/java/com/css/intelli/mapper/");
        pathMap.put(ConstVal.XML, projectPath + "/rd-intelli-service/src/main/resources/mybatis/mapper/");
        pathMap.put(ConstVal.SERVICE_IMPL, projectPath + "/rd-intelli-service/src/main/java/com/css/intelli/service/impl/");
        pathMap.put(ConstVal.CONTROLLER, projectPath + "/rd-intelli-web/src/main/java/com/css/intelli/controller/");
        packageConfig.setPathInfo(pathMap);
        return packageConfig;
    }

    public static TemplateConfig initTemplate() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
        return templateConfig;
    }

    public static InjectionConfig initInjection(PackageConfig packageConfig, String projectPath) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String mapperXmlPath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        //String templatePath = "/templates/mapper.xml.vm";

        String mapperJavaPath = "/templates/mapper.java.ftl";
        String entityPath= "/templates/entity.java.ftl";
        String servicePath = "/templates/service.java.ftl";
        String serviceImplPath = "/templates/serviceImpl.java.ftl";
        String controllerPath = "/templates/controller.java.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return packageConfig.getPathInfo().get(ConstVal.XML) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig(mapperJavaPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径及文件名
                return packageConfig.getPathInfo().get(ConstVal.MAPPER) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(entityPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径及文件名
                return packageConfig.getPathInfo().get(ConstVal.ENTITY) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(servicePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径及文件名
                return packageConfig.getPathInfo().get(ConstVal.SERVICE) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(serviceImplPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径及文件名
                return packageConfig.getPathInfo().get(ConstVal.SERVICE_IMPL) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(controllerPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径及文件名
                return packageConfig.getPathInfo().get(ConstVal.CONTROLLER) /*+ packageConfig.getModuleName()
                        + "/"*/ + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });

        /*cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });*/

        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public static StrategyConfig initStrategy(String[] tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix("T_");
        strategy.setFieldPrefix("F_");
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            strategy.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategy.setInclude(tableNames);
        }
        strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        return strategy;
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(initGlobal(projectPath));
        // 数据源配置
        mpg.setDataSource(initDataSource());
        // 包配置
        mpg.setPackageInfo(initPackage(projectPath));
        // 配置模板
        mpg.setTemplate(initTemplate());
        // 自定义配置
        mpg.setCfg(initInjection(initPackage(projectPath), projectPath));
        // 策略配置
        mpg.setStrategy(initStrategy(tableNames));
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}