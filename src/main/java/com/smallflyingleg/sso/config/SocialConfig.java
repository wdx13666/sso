/*
package com.smallflyingleg.sso.config;

import com.smallflyingleg.sso.utils.CustomSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

        //这里需要数据库创建表,建表语句,请查看 JdbcUsersConnectionRepository.sql
        //第一个参数数据源，第二个参数加载不同的ConnectionFactory，第三个参数加密
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        return jdbcUsersConnectionRepository;
    }

    @Bean(name = "customSocialConfigure")
    public SpringSocialConfigurer customSocialConfigure(){
        CustomSpringSocialConfigurer customSpringSocialConfigurer = new CustomSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        return customSpringSocialConfigurer;
    }*/
