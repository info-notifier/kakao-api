package com.arimi.kakaoapi.contoroller

import com.arimi.kakaoapi.service.ReplyService
import com.arimi.kakaoapi.vo.RequestMessageVO
import com.arimi.kakaoapi.vo.ReplyVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class Controller @Autowired constructor (
        private val replyService: ReplyService
) {
    @PostMapping("/message")
    fun message(@RequestBody body: RequestMessageVO): ReplyVO? {
        return try {
            replyService.getReplyByContent(body.content)
        } catch (e: RuntimeException) {
            System.err.println(e)
            throw e
        }
    }
}