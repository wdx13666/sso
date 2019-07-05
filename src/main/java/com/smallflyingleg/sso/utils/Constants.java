package com.smallflyingleg.sso.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

/**
 * 常量配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "constants")
public class  Constants {

    @NotEmpty
    private String  qqAppId;

    @NotEmpty
    private String qqAppSecret;

    @NotEmpty
    private String qqRedirectUrl;

    @NotEmpty
    private String weCatAppId;

    @NotEmpty
    private String weCatAppSecret;

    @NotEmpty
    private String weCatRedirectUrl;
}
