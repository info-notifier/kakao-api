package com.arimi.kakaoapi.contoroller

import com.arimi.kakaoapi.service.MessageServiceImpl
import com.arimi.kakaoapi.vo.RequestMessageVO
import com.arimi.kakaoapi.vo.ResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class Controller @Autowired constructor (
        private val msgService: MessageServiceImpl
) {
    @PostMapping("/message")
    fun message(@RequestBody body: RequestMessageVO): ResponseVO {
        return msgService.getMessage(body.content)
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}