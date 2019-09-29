package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.dao.MetaDataDAO
import com.arimi.kakaoapi.dao.ScrappedTextDAO
import com.arimi.kakaoapi.utils.ButtonManager
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.utils.Crawler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class ReplyRepositoryImpl @Autowired constructor(
        // scrappedTextDAO --> cache
        private val scrappedTextDAO: ScrappedTextDAO,
        private val metaDataDAO: MetaDataDAO,
        private val bm: ButtonManager
) : ReplyRepository {

    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO
    override lateinit var vacancyMetadata: MetaDataForResponseVO
    override lateinit var foodCourtMetadata: MetaDataForResponseVO


    // TODO: findInitialMenuReply, findVacancyMenuReply 코드 중복 묶을 수 있을 듯
    override fun findKeyboardInitReply(): KeyboardVO {
        metaDataDAO.getMetaData("keyboardInit").let {
            return try {
                KeyboardVO(it.type, it.buttons)
            } catch (e: UninitializedPropertyAccessException) {
                throw e
            }
        }
    }

    override fun findVacancyMenuReply(): ReplyVO {
        metaDataDAO.getMetaData("vacancyMenu").let {
            message = MessageVO(it.fixedText!!)
            keyboard = KeyboardVO(it.type, it.buttons)
        }
        return try {
            ReplyVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    override fun findFoodCourtMenuReply(): ReplyVO {
        metaDataDAO.getMetaData("foodCourtMenu").let {
            val buttons = bm.getFoodCourtFilteredButton(it.buttons)
            // 모든 식당 메뉴 없을 시 운영 x 메시지 던지기
            message = MessageVO(it.fixedText!!)
            keyboard = KeyboardVO(it.type, buttons)
        }
        return try {
            ReplyVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    override fun findInitialMenuReply(): ReplyVO {
        metaDataDAO.getMetaData("initialMenu").let {
            message = MessageVO(it.fixedText!!)
            keyboard = KeyboardVO(it.type, it.buttons)
        }

        return try {
            ReplyVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    override fun findVacancyReply(place: String): ReplyVO {
        val text = scrappedTextDAO.getText(place)
        val timestamp = Timestamp(System.currentTimeMillis())
        vacancyMetadata = metaDataDAO.getMetaData(place)

        vacancyMetadata.let {
            photo = PhotoVO("${it.imgUrl!!}?t=${timestamp.time}", 720, 630)
            msgBtn = MessageButtonVO("상세정보", it.imgUrl)
            message = MessageVO(text, photo, msgBtn)
            keyboard = KeyboardVO(it.type, it.buttons)
        }
        // TODO: imgUrl, buttons가 null로 들어오는 경우 exception 처리
        return try {
            ReplyVO(message, keyboard)
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    override fun findPlugReply(): ReplyVO {
        vacancyMetadata = metaDataDAO.getMetaData("plug")

        vacancyMetadata.let {
            photo = PhotoVO(it.imgUrl!!, 720, 200)
            message = MessageVO(it.fixedText!!, photo)
            keyboard = KeyboardVO(it.type, it.buttons)
        }

        return ReplyVO(message, keyboard)
    }

    override fun findFoodCourtReply(place: String): ReplyVO {
        // 빈 텍스트 반환 시
        val text = scrappedTextDAO.getText(place)
        foodCourtMetadata = metaDataDAO.getMetaData(place)

        foodCourtMetadata.let {
            val buttons = bm.getFoodCourtFilteredButton(it.buttons)

            message = MessageVO(text)
            keyboard = KeyboardVO(it.type, buttons)
        }
        return ReplyVO(message, keyboard)
    }
}