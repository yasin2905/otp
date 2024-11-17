package com.otp.otpserver.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.otp.otpserver.model.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${otp.expireTimeInSeconds}")
    private int expireTime;

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        Caffeine cacheSpec = Caffeine.newBuilder()
                .expireAfterWrite(expireTime, TimeUnit.SECONDS);
        cacheManager.setCaffeine(cacheSpec);
        return cacheManager;
    }
    @Bean( "AuthenticationList")
    public Map<String, String> getAuthList() {
        return   new HashMap<String, String>();
    }
    @Bean( "RegisteredList")
    public Map<String, RegisterRequestDto> getRegisteredList() {
        return   new HashMap<String, RegisterRequestDto>();
    }
}