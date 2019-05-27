package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.vo.ResponseVO

interface MessageService {
    fun getMessage(content: String): ResponseVO
}