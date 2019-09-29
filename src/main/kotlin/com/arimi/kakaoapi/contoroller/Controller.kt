package com.arimi.kakaoapi.contoroller

import com.arimi.kakaoapi.service.ChannelService
import com.arimi.kakaoapi.vo.KeyboardVO
import com.arimi.kakaoapi.vo.RequestMessageVO
import com.arimi.kakaoapi.vo.ReplyVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class Controller @Autowired constructor (
        private val channelService: ChannelService
) {
    @GetMapping("/keyboard")
    fun keyboard(): KeyboardVO {
        return try {
            channelService.getKeyboardInitReply()
        } catch (e: RuntimeException) {
            System.err.println(e)
            throw e
        }
    }

    @PostMapping("/message")
    fun message(@RequestBody body: RequestMessageVO): ReplyVO? {
        return try {
            channelService.getReplyByContent(body.content)
        } catch (e: RuntimeException) {
            System.err.println(e)
            throw e
        }
    }
}