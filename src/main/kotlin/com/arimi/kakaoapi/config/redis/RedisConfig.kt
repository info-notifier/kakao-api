package com.arimi.kakaoapi.config.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:")
@Profile("dev")
class RedisDevConfig {

//    @Bean

}