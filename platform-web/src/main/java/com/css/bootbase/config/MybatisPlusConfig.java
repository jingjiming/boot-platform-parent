package com.css.bootbase.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Data
@EnableTransactionManagement
@ConditionalOnClass(value = {MybatisPlusInterceptor.class})
@ConfigurationProperties(
        prefix = "spring.datasource.dynamic.datasource.master"
)
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置文件中的数据库配置信息
     * master主库
     */
    public String datasourceUrl = "jdbc:dm";

    public String driverClassName = "";

    public String username = "";

    public String password = "";


    /**
     * mybatis-plus分页插件<br>
     * 文档：https://mp.baomidou.com/guide/page.html <br>
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        String[] urlInfos = datasourceUrl.split(":");
        //根据数据库配置生成插件
        if(urlInfos != null && urlInfos.length >1){
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.getDbType(urlInfos[1])));
        }
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
                configuration.setCacheEnabled(true);
                configuration.setMapUnderscoreToCamelCase(true);
                configuration.setCallSettersOnNulls(true);
                // 同配置文件mybatis-plus:configuration:jdbc-type-for-null: 'null'
                // 或在实体类对应字段上加注解@TableField(strategy=FieldStrategy.IGNORED)，忽略null值的判断
                configuration.setJdbcTypeForNull(JdbcType.NULL);
        };
    }

}
