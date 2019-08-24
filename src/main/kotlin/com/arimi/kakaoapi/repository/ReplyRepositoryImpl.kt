package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.dao.VacancyDAO
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class ReplyRepositoryImpl : ReplyRepository {
    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO

    override fun findVacancyReply(place: String): ReplyVO {
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
        // TODO: imgUrl, buttons가 null로 들어오는 경우 exception 처리

        return try {
            ReplyVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    override fun findPlugReply(): ReplyVO {
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

        return ReplyVO(message, keyboard)
    }

    override fun findFoodCourtReply(place: String): ReplyVO {
        return ReplyVO(message, keyboard)
    }
}