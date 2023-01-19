package com.css.bootbase;

import com.css.common.banner.SystemEnvBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;

/**
 * Created by jiming.jing on 2023/1/18
 */
@SpringBootApplication
@EnableOpenApi
@EnableScheduling //开启定时任务
@EnableAsync
@ComponentScan(basePackages = {"com.css"})
@MapperScan(basePackages = {"com.css.bootbase.mapper"})
public class BootBaseWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BootBaseWebApplication.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootBaseWebApplication.class);
    }

    @Override
    public void run(String... args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>> 服务启动完成 >>>>>>>>>>>>>>>>>");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(BootBaseWebApplication.class);
        application.setBanner(new SystemEnvBanner());
        ConfigurableApplicationContext context = application.run(args);

        Environment env = context.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        port = port == null ? "8080" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        log.info("\n----------------------------------------------------------\n\t" +
                "SpringBoot Application is running! Access URLs:\n\t" +
                "本地访问地址: \thttp://localhost:" + port + path + "\n\t" +
                "外部访问地址: \thttp://" + ip + ":" + port + path + "\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "doc.html\n" +
                "----------------------------------------------------------");
        //SpringApplication.run(BootBaseWebApplication.class, args);
    }
}
