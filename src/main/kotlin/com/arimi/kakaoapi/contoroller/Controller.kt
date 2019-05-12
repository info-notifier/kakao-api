package com.arimi.kakaoapi.contoroller

import com.arimi.kakaoapi.service.MessageServiceImpl
import com.arimi.kakaoapi.vo.RequestMessageVO
import com.arimi.kakaoapi.vo.ResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class Controller @Autowired constructor (
        private val msgService: MessageServiceImpl
) {
    @PostMapping("/message")
    fun message(@RequestBody body: RequestMessageVO): ResponseVO? {
        return try {
            msgService.getMessage(body.content)
        } catch (e: RuntimeException) {
            System.err.println(e)
            throw e
        }
    }

    @GetMapping("/test")
    fun test(): String = "hi test"
}