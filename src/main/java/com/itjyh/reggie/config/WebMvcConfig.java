package com.itjyh.reggie.config;/**
 * @Author: jiayuhang
 * @Date: 2022/09/14/10:44
 * @Description:
 */

import com.itjyh.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO 设置静态资源映射
 * @date 2022/9/14 10:44
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

    }

    /*
     * @Author jiayuhang
     * @Date 9:02 2022/9/15
     * @Param 扩展mvc框架消息转化器
     * @return
     * 解决启用禁用返回long型 id精度问题.以及日期显示问题
     **/
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info(("扩展消息转换器"));
        //        创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        设置转换器,Jackson将Java转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }
}
