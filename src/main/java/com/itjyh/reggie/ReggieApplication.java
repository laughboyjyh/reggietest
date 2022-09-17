package com.itjyh.reggie;/**
 * @Author: jiayuhang
 * @Date: 2022/09/14/10:38
 * @Description:
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/14 10:38
 */

@Slf4j  //可以直接使用log打印日志
@SpringBootApplication
//过滤器必备
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
    log.info("项目启动成功");
    }

}
