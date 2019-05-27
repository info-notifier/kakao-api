package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.exception.WrongRequestException
import com.arimi.kakaoapi.repository.VacancyReplyRepository
import com.arimi.kakaoapi.vo.ResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl @Autowired constructor (
        private val vacancy: VacancyReplyRepository
) : MessageService {
    override fun getMessage(content: String): ResponseVO {
        return when (content) {
            "C1 열람실" -> vacancy.getReply("C1")
            "D1 열람실" -> vacancy.getReply("D1")
            "C1 열람실 플러그 위치" -> vacancy.getPlugMessage()
            else -> throw WrongRequestException("BAD REQUEST")
        }
    }
}