package com.arimi.kakaoapi.utils

import com.arimi.kakaoapi.libs.scrapers.FoodCourtScraper
import com.arimi.kakaoapi.libs.scrapers.VacancyScraper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Crawler @Autowired constructor (
        val vacancy: VacancyScraper,
        val foodCourt: FoodCourtScraper
)