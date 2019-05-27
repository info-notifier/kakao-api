package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*

interface AutoReplyRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    var msgBtn: MessageButtonVO
    var photo: PhotoVO
    
    fun getMessage(place :String): ResponseVO
}