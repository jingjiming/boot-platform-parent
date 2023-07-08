package com.css.bootbase.oss.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.css.bootbase.oss.core.OssProperties;
import com.css.bootbase.oss.core.OssTemplate;
import com.css.bootbase.oss.service.OssTemplateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配配置类，自动装配的bean有AmazonS3和OssTemplate
 *
 * @author jiming.jing
 * @date 2023/7/1 00:17
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {
    /**
     * ConditionalOnMissingBean是修饰bean的一个注解，当你的bean被注册之后，注册相同类型的bean，就不会成功，它会保证你的bean只有一个，即实例只有一个，多个会报错。
     * @param ossProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 ossClient(OssProperties ossProperties) {
        // 客户端配置，主要是全局的配置信息
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(ossProperties.getMaxConnections());
        // url以及region配置
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                ossProperties.getEndpoint(), ossProperties.getRegion());
        // 凭证配置
        AWSCredentials awsCredentials = new BasicAWSCredentials(ossProperties.getAccessKey(),
                ossProperties.getSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        // build amazonS3Client客户端
        return AmazonS3Client.builder().withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration).withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding().withPathStyleAccessEnabled(ossProperties.getPathStyleAccess()).build();
    }

    @Bean
    @ConditionalOnBean(AmazonS3.class)
    public OssTemplate ossTemplate(AmazonS3 amazonS3) {
        return new OssTemplateImpl(amazonS3);
    }
}
