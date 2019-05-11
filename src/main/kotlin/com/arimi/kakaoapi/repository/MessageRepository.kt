package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*

interface MessageRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    // 아래 두 멤버 삭제 생각해보기
    var msgBtn: MessageButtonVO?
    var photo: PhotoVO?
    
    fun getMessage(place :String): ResponseVO
}