package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.dao.VacancyDAO
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class VacancyMessageRepository : MessageRepository {
    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO

    override fun getMessage(place: String): ResponseVO {
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

    fun getPlugMessage(): ResponseVO {
        val text = " * 플러그의 위치에 병아리가 있어요.\n" +
                "왼쪽 병아리부터 플러그와 가까운 자리 번호입니다.\n" +
                "(하단의 이미지는 실시간 이미지가 아닙니다.)\n" +
                "349, 380, 405 or 412, 444, 468, 473"
        val imgUrl = VacancyDAO.getMetaDataFor["plug"]?.imgUrl
        val buttons = VacancyDAO.getMetaDataFor["plug"]?.buttons

        imgUrl?.let {
            photo = PhotoVO(it, 720, 200)
            message = MessageVO(text, photo)
        }
        buttons?.let { keyboard = KeyboardVO("buttons", buttons) }

        return ResponseVO(message, keyboard)
    }
}