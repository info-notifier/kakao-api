package com.arimi.kakaoapi.contorollers

import com.arimi.kakaoapi.*
import org.springframework.web.bind.annotation.*

@RestController
class Controller {
    @PostMapping("/message")
    fun message(@RequestBody body: RequestMessageVO): ResponseVO {
        return when (body.content) {
            "C1 열람실" -> Crawler.Vacancy.of("C1")
            "D1 열람실" -> Crawler.Vacancy.of("D1")
            "C1 열람실 플러그 위치" -> Crawler.Vacancy.of("C1")
            else -> Crawler.Vacancy.of("C1")
        }
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}