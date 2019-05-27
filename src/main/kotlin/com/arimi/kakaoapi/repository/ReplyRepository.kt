package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*

interface ReplyRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    var msgBtn: MessageButtonVO
    var photo: PhotoVO
    
    fun findVacancyReply(place: String): ReplyVO
    fun findPlugReply(): ReplyVO
}