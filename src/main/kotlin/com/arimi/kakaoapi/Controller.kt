package com.arimi.kakaoapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @GetMapping("/crawl")
    fun page(@RequestParam(value = "url", defaultValue = "http://www.ajou.ac.kr/main/life/food.jsp") url: String): String {

//        Crawler.FoodCourt.of("dorm")
        Crawler.Vacancy.of("D1")
        return "hi"
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}