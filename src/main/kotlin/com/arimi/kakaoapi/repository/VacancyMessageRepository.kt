package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class VacancyMessageRepository : MessageRepository {
    private data class MetaData(val url: String, val buttons: List<String>)
    private val getMetaDataFor: HashMap<String, MetaData> = hashMapOf (
            "C1" to MetaData (
                    "http://u-campus.ajou.ac.kr/ltms/temp/241.png",
                    listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
            ),
            "D1" to MetaData (
                    "http://u-campus.ajou.ac.kr/ltms/temp/261.png",
                    listOf("C1 열람실", "D1 열람실", "처음으로")
            )
    )

    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO

    override fun getMessage(place: String): ResponseVO {
        val text = Crawler.Vacancy.getTextOf(place)
        val url = getMetaDataFor[place]?.url
        val buttons = getMetaDataFor[place]?.buttons
        val timestamp = Timestamp(System.currentTimeMillis())

        url?.let {
            photo = PhotoVO("$url?=${timestamp.time}", 720, 630)
            msgBtn = MessageButtonVO("상세정보", it)
            message = MessageVO(text, msgBtn, photo)
        }
        buttons?.let { keyboard = KeyboardVO("buttons", buttons) }

        // url, buttons가 null로 들어오는 경우 exception 처리 어떻게할까 뭐 그럴리는 없지만..
        // F1 열람실 같은 경우 C1처럼나오네
        return try {
            ResponseVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            println("here")
            throw e
        }
    }
}