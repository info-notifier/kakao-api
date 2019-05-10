package com.arimi.kakaoapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp

data class Photo (
        val url: String,
        val width: Int,
        val height: Int
)
data class MessageButton (
        val label: String,
        val url: String
)
data class Keyboard (
        val type: String,
        val buttons: List<String>
)
data class Message (
        val text: String,
        val photo: Photo,
        val message_button: MessageButton
)
data class Response (
        val message: Message,
        val keyboard: Keyboard
)

@RestController
class Controller {
    @GetMapping("/crawl")
    fun page(@RequestParam(value = "url", defaultValue = "http://www.ajou.ac.kr/main/life/food.jsp") url: String): Response {
//        Crawler.FoodCourt.of("dorm")
//        val foo = Crawler.Vacancy.of("D1")
        val timestamp = Timestamp(System.currentTimeMillis());

        val msgBtn = MessageButton("상세정보", "http://u-campus.ajou.ac.kr/ltms/temp/261.png")
        val photo = Photo("http://u-campus.ajou.ac.kr/ltms/temp/261.png?t=${timestamp.time}", 720, 630)
        val vacancy = Crawler.Vacancy.of("D1")

        val message = Message(vacancy, photo, msgBtn)
        val keyboard = Keyboard("buttons", listOf("C1 열람실", "처음으로"))

        return Response(message, keyboard)
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}