package com.yqx.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo        // 开启支持Dubbo注解
@MapperScan( "com.yqx.provider.mapper" ) // 配置扫描包 扫描mapper包下的注解
//使用xml配置的时候 需要加上该注解 启动Spring的时候 扫描spring-dubbo.xml文件
@ImportResource(locations = "classpath:spring-dubbo.xml" )
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
