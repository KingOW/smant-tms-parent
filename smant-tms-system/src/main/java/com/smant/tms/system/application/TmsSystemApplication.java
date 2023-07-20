package com.smant.tms.system.application;

import com.smant.auth.client.SimpleAuthClient;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.SpringVersion;
/**
 * 启动类
 */
@SpringBootApplication(
        scanBasePackages = {"com.smant.tms.system","com.smant.sdk",
                             "com.smant.common.web",
                            "com.smant.common.security",
                            "com.alibaba.druid.spring.boot"},
        exclude = DataSourceAutoConfiguration.class)
@MapperScan(value = "com.smant.tms.system.dao.mapper")
@EnableFeignClients(basePackages = {"com.smant.auth.client"})
@Slf4j
public class TmsSystemApplication {
    public static void main(String[] args) {
            new SpringApplicationBuilder(TmsSystemApplication.class)
                    .main(SpringVersion.class)
                    .bannerMode(Banner.Mode.CONSOLE)
                    .run(args);
        }
}
