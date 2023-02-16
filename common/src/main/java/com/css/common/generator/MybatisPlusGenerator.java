package com.css.common.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.DmTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.DMQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by jiming.jing on 2023/2/1
 */
public class MybatisPlusGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入表名，多个英文逗号分割：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的表名，多个英文逗号分割：");
    }

    /**
     * <p>
     * 数据源配置
     * </p>
     *
     * @return
     */
    public static DataSourceConfig.Builder dataSourceConfig() {
        DataSourceConfig.Builder builder = new DataSourceConfig
                .Builder(
                "jdbc:dm://localhost:5236/m_db_product?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8",
                "PRODUCT",
                "Admin123!@#")
                .dbQuery(new DMQuery())
                .typeConvert(new DmTypeConvert());
        return builder;
    }

    public static void main(String[] args) {
        System.out.println("\t------mybatis-plus代码生成器------");
        System.out.println("\t---------version:3.5.2----------");
        String projectPath = System.getProperty("user.dir");
        String tableNames = scanner();

        // 多modules工程配置，单体工程无需此配置
        HashMap<OutputFile, String> pathMap = new HashMap<OutputFile, String>() {
            {
                put(OutputFile.entity, projectPath + "/model/src/main/java/com/css/bootbase/entity/");
                put(OutputFile.service, projectPath + "/service/src/main/java/com/css/bootbase/service/");
                put(OutputFile.serviceImpl, projectPath + "/service/src/main/java/com/css/bootbase/service/impl/");
                put(OutputFile.mapper, projectPath + "/service/src/main/java/com/css/bootbase/mapper/");
                put(OutputFile.xml, projectPath + "/service/src/main/resources/mybatis/mapper/");
                put(OutputFile.controller, projectPath + "/web/src/main/java/com/css/bootbase/controller/");

            }
        };

        FastAutoGenerator.create(dataSourceConfig())
                //.create("jdbc:dm://localhost:5236/m_db_product?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8", "PRODUCT", "Admin123!@#")
                // 全局配置
                .globalConfig(builder -> builder
                        // 禁止打开输出目录
                        .disableOpenDir()
                        // 设置输出目录
                        //.outputDir(System.getProperty("user.dir") + "/src/main/java")
                        // 设置作者
                        .author("jiming.jing")
                        // 开启Swagger模式
                        .enableSwagger()
                        // 实体类中时间策略
                        .dateType(DateType.TIME_PACK)
                        // 生成日期
                        .commentDate("yyyy/MM/dd")
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("com.css.bootbase")
                        .pathInfo(pathMap)
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(tableNames)
                            // 增加过滤表前缀
                            .addTablePrefix("T_")
                            // 增加过滤字段前缀
                            //.addFieldPrefix("F_")

                            // 实体策略配置
                            .entityBuilder()
                            // 开启Lombok模型
                            .enableLombok()
                            // 开启生成实体时生成字段注解
                            .enableTableFieldAnnotation()
                            // 乐观锁字段名（数据库）
                            .versionColumnName("version")
                            // 乐观锁属性名（实体）
                            .versionPropertyName("version")
                            // 逻辑删除字段名（数据库）
                            .logicDeleteColumnName("del_flag")
                            // 逻辑删除属性名（实体）
                            .logicDeletePropertyName("del_flag")
                            // 数据库表映射到实体的命名策略 -- 下划线转驼峰命名
                            .naming(NamingStrategy.underline_to_camel)
                            // 数据库表字段映射到实体的命名策略 -- 下划线转驼峰命名
                            .columnNaming(NamingStrategy.underline_to_camel)
                            // 开发规范之创建时间、更新时间 交由mybatis-plus处理，若由数据库处理，则取消此设置
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("last_time", FieldFill.INSERT_UPDATE)
                            )

                            // mapper策略配置
                            .mapperBuilder()
                            // 设置父类
                            .superClass(BaseMapper.class)
                            // 格式化 mapper 文件名称
                            .formatMapperFileName("%sMapper")
                            // 格式化 xml 文件名称
                            .formatXmlFileName("%sMapper")
                            // 开启 @Mapper注解
                            .enableMapperAnnotation()
                            // 生成通用的 BaseResultMap
                            .enableBaseResultMap()

                            // service策略配置
                            .serviceBuilder()
                            // 格式化 service 接口文件名称
                            .formatServiceFileName("%sService")
                            // 格式化 service.impl 实现类文件名称
                            .formatServiceImplFileName("%sServiceImpl")

                            // controller策略配置
                            .controllerBuilder()
                            // 开启生成@RestController控制器
                            .enableRestStyle();
                })

                .templateEngine(new FreemarkerTemplateEngine())

                .execute();

    }
}
