package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*

interface MessageRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    var msgBtn: MessageButtonVO
    var photo: PhotoVO
    
    fun getMessage(place :String): ResponseVO
}