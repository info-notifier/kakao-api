package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.dao.VacancyDAO
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class VacancyReplyRepository : ReplyRepository {
    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO

    override fun findReply(place: String): ResponseVO {
        val text = Crawler.Vacancy.getTextOf(place)
        val imgUrl = VacancyDAO.getMetaDataFor[place]?.imgUrl
        val buttons = VacancyDAO.getMetaDataFor[place]?.buttons
        val timestamp = Timestamp(System.currentTimeMillis())
        imgUrl?.let {
            photo = PhotoVO("$it?=${timestamp.time}", 720, 630)
            msgBtn = MessageButtonVO("상세정보", it)
            message = MessageVO(text, photo, msgBtn)
        }
        buttons?.let { keyboard = KeyboardVO("buttons", it) }
        // imgUrl, buttons가 null로 들어오는 경우 exception 처리 어떻게할까 뭐 그럴리는 없지만..
        // F1 열람실 같은 경우 C1처럼나오네
        return try {
            ResponseVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            println("here")
            throw e
        }
    }

}