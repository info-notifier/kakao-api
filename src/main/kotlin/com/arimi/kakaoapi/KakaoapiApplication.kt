package com.arimi.kakaoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

// @SpringBootApplication 어노테이션은
// @Configuration, @EnableAutoConfiguration 그리고 @ComponentScan을 디폴트 속성으로 함께 사용하는 것과 같다.
@SpringBootApplication
@EnableScheduling
class KakaoapiApplication

fun main(args: Array<String>) {
    runApplication<KakaoapiApplication>(*args)
}
