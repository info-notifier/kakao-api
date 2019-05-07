package com.arimi.kakaoapi

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @GetMapping("/crawl")
    fun page(@RequestParam(value = "url", defaultValue = "http://www.ajou.ac.kr/main/life/food.jsp") url: String): String {

        println(Crawler.FoodCourtOf.student())
        return "hi"
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}