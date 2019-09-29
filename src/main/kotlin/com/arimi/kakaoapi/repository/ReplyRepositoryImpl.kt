package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.dao.MetaDataDAO
import com.arimi.kakaoapi.dao.ScrappedTextDAO
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.utils.Crawler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class ReplyRepositoryImpl @Autowired constructor(
        // scrappedTextDAO --> cache
        val scrappedTextDAO: ScrappedTextDAO,
        val metaDataDAO: MetaDataDAO
) : ReplyRepository {

    override lateinit var message: MessageVO
    override lateinit var keyboard: KeyboardVO
    override lateinit var msgBtn: MessageButtonVO
    override lateinit var photo: PhotoVO
    override lateinit var vacancyMetadata: MetaDataForResponseVO
    override lateinit var foodCourtMetadata: MetaDataForResponseVO

    override fun findKeyboardInitReply(): KeyboardVO {
        metaDataDAO.getMetaData("keyboardInit").let {
            return try {
                KeyboardVO(it.type, it.buttons)
            } catch (e: UninitializedPropertyAccessException) {
                throw e
            }
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
        val text = scrappedTextDAO.getText(place)
        foodCourtMetadata = metaDataDAO.getMetaData(place)

        foodCourtMetadata.let {
            message = MessageVO(text)
            keyboard = KeyboardVO(it.type, it.buttons)
        }
        return ReplyVO(message, keyboard)
    }
}