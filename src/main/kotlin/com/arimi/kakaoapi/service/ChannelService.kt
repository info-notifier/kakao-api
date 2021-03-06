package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.vo.KeyboardVO
import com.arimi.kakaoapi.vo.ReplyVO

interface ChannelService {
    fun getKeyboardInitReply(): KeyboardVO
    fun getReplyByContent(content: String): ReplyVO
}