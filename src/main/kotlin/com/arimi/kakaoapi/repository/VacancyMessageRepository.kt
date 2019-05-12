package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class VacancyMessageRepository : MessageRepository {
    private data class MetaData(val imgUrl: String, val buttons: List<String>)
    private val getMetaDataFor: HashMap<String, MetaData> = hashMapOf (
            "C1" to MetaData (
                    "http://u-campus.ajou.ac.kr/ltms/temp/241.png",
                    listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
            ),
            "D1" to MetaData (
                    "http://u-campus.ajou.ac.kr/ltms/temp/261.png",
                    listOf("C1 열람실", "D1 열람실", "처음으로")
            ),
            "plug" to MetaData (
                    "https://user-images.githubusercontent.com/31656287/57567720-87545980-7418-11e9-96be-d6f3aaca74b6.png",
                    listOf("C1 열람실", "D1 열람실", "처음으로")
            )
    )

    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO

    override fun getMessage(place: String): ResponseVO {
        val text = Crawler.Vacancy.getTextOf(place)
        val imgUrl = getMetaDataFor[place]?.imgUrl
        val buttons = getMetaDataFor[place]?.buttons
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
        val imgUrl = getMetaDataFor["plug"]?.imgUrl
        val buttons = getMetaDataFor["plug"]?.buttons

        imgUrl?.let {
            photo = PhotoVO(it, 720, 200)
            message = MessageVO(text, photo)
        }
        buttons?.let { keyboard = KeyboardVO("buttons", buttons) }

        return ResponseVO(message, keyboard)
    }
}