package com.smant.tms.tenant.application;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.SpringVersion;
//import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 启动类
 */
@SpringBootApplication(
        scanBasePackages = {"com.smant.tms.tenant","com.smant.sdk","com.smant.common.web","com.alibaba.druid.spring.boot"},
        exclude = DataSourceAutoConfiguration.class)
@MapperScan(value = "com.smant.tms.tenant.dao.mapper")
@Slf4j
public class TmsTenantApplication {
    public static void main(String[] args) {
            new SpringApplicationBuilder(TmsTenantApplication.class)
                    .main(SpringVersion.class)
                    .bannerMode(Banner.Mode.CONSOLE)
                    .run(args);
    }
}
