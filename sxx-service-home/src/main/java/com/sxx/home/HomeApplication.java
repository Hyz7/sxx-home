package com.sxx.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行官网数据查询启动类〉
 *
 * @author hyz
 * @create 2019/1/9 0009
 * @since 1.0.0
 */
@SpringBootApplication
@EnableSwagger2
@EntityScan("com.sxx.framework.domain")//扫描实体类
@ComponentScan(basePackages = {"com.sxx.api"})//扫描接口
@ComponentScan(basePackages = {"com.sxx.framework"})//扫描common
@ComponentScan(basePackages = {"com.sxx.home"})//扫描本项目下的所有类
public class HomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeApplication.class,args);
    }
}
