package com.arimi.kakaoapi.utils

import com.arimi.kakaoapi.libs.scrapers.FoodCourtScraper
import com.arimi.kakaoapi.libs.scrapers.VacancyScraper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Crawler @Autowired constructor (
        redisTemplate: StringRedisTemplate,
        val vacancy: VacancyScraper,
        val foodCourt: FoodCourtScraper
) {
    private val ops = redisTemplate.opsForValue()
    private val vacancyPlaces = listOf("C1", "D1")
    private val foodCourtPlaces = listOf("student", "dormitory", "faculty")

    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60)
    fun setVacancy() {
        vacancyPlaces.forEach{place ->
            val text = vacancy.getTextOf(place)
            ops.set(place, text)
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60)
    fun setFoodCourt() {
        foodCourtPlaces.forEach{place ->
            val text = foodCourt.getTextOf(place)
            ops.set(place, text)
        }
    }
}