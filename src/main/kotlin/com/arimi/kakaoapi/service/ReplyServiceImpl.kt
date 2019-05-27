package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.exception.WrongRequestException
import com.arimi.kakaoapi.repository.ReplyRepository
import com.arimi.kakaoapi.vo.ReplyVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl @Autowired constructor (
        private val replyRepository: ReplyRepository
) : ReplyService {
    override fun getReplyByContent(content: String): ReplyVO {
        return when (content) {
            "C1 열람실" -> replyRepository.findVacancyReply("C1")
            "D1 열람실" -> replyRepository.findVacancyReply("D1")
            "C1 열람실 플러그 위치" -> replyRepository.findPlugReply()
            else -> throw WrongRequestException("BAD REQUEST")
        }
    }
}