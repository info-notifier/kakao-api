package com.arimi.kakaoapi.contoroller

import com.arimi.kakaoapi.service.ReplyService
import com.arimi.kakaoapi.vo.KeyboardVO
import com.arimi.kakaoapi.vo.RequestMessageVO
import com.arimi.kakaoapi.vo.ReplyVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class Controller @Autowired constructor (
        private val replyService: ReplyService
) {
    @GetMapping("/keyboard")
    fun keyboard(): KeyboardVO {
        return try {
            replyService.getKeyboardInitReply()
        } catch (e: RuntimeException) {
            System.err.println(e)
            throw e
        }
    }

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