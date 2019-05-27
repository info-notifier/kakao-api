package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.vo.ReplyVO

interface ReplyService {
    fun getReplyByContent(content: String): ReplyVO
}