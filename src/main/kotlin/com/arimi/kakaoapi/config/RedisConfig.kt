package com.arimi.kakaoapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@PropertySource("classpath:application.properties")
class RedisConfig {

    @Value("\${spring.redis.host}")
    lateinit var redisHost: String

    @Value("\${spring.redis.port}")
    val redisPort: Int = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val lettuceConnectionFactory = LettuceConnectionFactory()
        // setter 이용방식은 deprecated 됨
        // 그런데 application.properties의 property(host, port 등)들을 자동으로 가져오지 못함
        // TODO: 위 문제점 원인 찾아 해결하기
        lettuceConnectionFactory.hostName = redisHost
        lettuceConnectionFactory.port = redisPort
        return lettuceConnectionFactory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        return redisTemplate
    }

    @Bean
    fun stringRedisTemplate(): StringRedisTemplate {
        val stringRedisTemplate = StringRedisTemplate()
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory())
        stringRedisTemplate.keySerializer = StringRedisSerializer()
        stringRedisTemplate.valueSerializer = StringRedisSerializer()
        return stringRedisTemplate
    }

}