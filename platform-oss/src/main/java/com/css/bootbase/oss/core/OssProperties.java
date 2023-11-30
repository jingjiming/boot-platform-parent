package com.css.bootbase.oss.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>OSS配置类</p>
 * <p>将配置文件中oss开头的属性绑定到此对象中</p>
 * Created by jiming.jing on 2023/6/30.
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * 区域
     */
    private String region;

    /**
     * true path-style nginx 反向代理和S3默认支持 pathStyle模式 {http://endpoint/bucketname}
     * false supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style 模式{http://bucketname.endpoint}
     * 只是url的显示不一样
     */
    private Boolean pathStyleAccess = true;

    /**
     * Access key
     */
    private String accessKey;

    /**
     * Secret key
     */
    private String secretKey;

    /**
     * 最大线程数，默认：100
     */
    private Integer maxConnections = 100;
}
