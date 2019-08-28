package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.dao.FoodCourtDAO
import com.arimi.kakaoapi.dao.VacancyDAO
import com.arimi.kakaoapi.vo.*

interface ReplyRepository {
    var message: MessageVO
    var keyboard: KeyboardVO
    var msgBtn: MessageButtonVO
    var photo: PhotoVO
    var vacancyMetadata: VacancyDAO.MetaData
    var foodCourtMetadata: FoodCourtDAO.MetaData
    
    fun findVacancyReply(place: String): ReplyVO
    fun findPlugReply(): ReplyVO
    fun findFoodCourtReply(place: String): ReplyVO
}