package com.arimi.kakaoapi.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisDAO @Autowired constructor(
        private val redisTemplate: StringRedisTemplate
) {
    private val ops = redisTemplate.opsForValue()

    // scraper 라이브러리는 어떤 경우에도 null을 반환하지 않기 때문에 !! 연산을 쓴다.
    // FoodCourtScraper의 경우 scrap할 텍스트가 없는 경우에는 빈 스트링을 반환하도록 구현하였음
    fun getScrappedText(place: String): String = ops.get(place)!!
    fun setScrappedText(key: String, value: String) = ops.set(key, value)
}