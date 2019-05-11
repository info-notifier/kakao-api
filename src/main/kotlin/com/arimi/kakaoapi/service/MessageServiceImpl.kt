package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.repository.VacancyMessageRepository
import com.arimi.kakaoapi.vo.ResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl @Autowired constructor (
        private val vacancy: VacancyMessageRepository
) : MessageService {
    override fun getMessage(content: String): ResponseVO {
        return when (content) {
            "C1 열람실" -> vacancy.getMessage("C1")
            "D1 열람실" -> vacancy.getMessage("D1")
            else -> vacancy.getMessage("D1")
        }
    }
}