package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*

interface ReplyRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    var msgBtn: MessageButtonVO
    var photo: PhotoVO
    var vacancyMetadata: MetaDataForResponseVO
    var foodCourtMetadata: MetaDataForResponseVO

    fun findKeyboardInitReply(): KeyboardVO
    fun findVacancyReply(place: String): ReplyVO
    fun findPlugReply(): ReplyVO
    fun findFoodCourtReply(place: String): ReplyVO
    fun findInitialMenuReply(): ReplyVO
}