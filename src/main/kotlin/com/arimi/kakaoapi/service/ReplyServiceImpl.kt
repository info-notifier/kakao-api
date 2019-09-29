package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.exception.WrongRequestException
import com.arimi.kakaoapi.repository.ReplyRepository
import com.arimi.kakaoapi.vo.KeyboardVO
import com.arimi.kakaoapi.vo.ReplyVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl @Autowired constructor (
        private val replyRepository: ReplyRepository
) : ReplyService {
    // 서비스는 여러개의 레포를 가져와서 사용하는 상위 레이어인데 네이밍이 reply로 replyrepository와 같은게 좀 이상함

    override fun getKeyboardInitReply(): KeyboardVO = replyRepository.findKeyboardInitReply()

    override fun getReplyByContent(content: String): ReplyVO {
        return when (content) {
            // TODO: 처음으로 버튼 분기 필요
            "C1 열람실" -> replyRepository.findVacancyReply("C1")
            "D1 열람실" -> replyRepository.findVacancyReply("D1")
            "C1 열람실 플러그 위치" -> replyRepository.findPlugReply()
            "학생식당" -> replyRepository.findFoodCourtReply("student")
            "기숙사식당" -> replyRepository.findFoodCourtReply("dormitory")
            "교직원식당" -> replyRepository.findFoodCourtReply("faculty")
            else -> throw WrongRequestException("BAD REQUEST")
        }
    }
}