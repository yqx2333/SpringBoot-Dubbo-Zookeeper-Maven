package com.yqx.prometheus.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// 测试普罗米修斯 专用API
@RestController // 返回Json字符串格式
public class TestController {

    // 记录日志用
    Logger logger = LoggerFactory.getLogger( TestController.class );

    /**
     * 测试方法 无意义
     * @return
     */
    @GetMapping( "/test" )
    public String test(){
        logger.info( "test" );
        return "ok";
    }

    /**
     * 测试方法 无意义
     * @return
     */
    @GetMapping( "" )
    public String home(){
        logger.info( "home" );
        return "ok";
    }


}
