package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.vo.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReplyRepositoryImplTests {
    @Mock
    lateinit var replyMock: ReplyVO

    @InjectMocks
    lateinit var repo: ReplyRepositoryImpl

    @Nested
    inner class VacancyTest {
        // Crawler.Vacancy.getTextOf("C1") 실행 여부도 판단하기
        @Test
        fun c1() {
            // 아래는 잘못된 케이스
            // -> replyMock.message를 레퍼하려는 순간 NullpointerException 발생

//            given(replyMock.message.photo).willReturn(
//                    PhotoVO("http://u-campus.ajou.ac.kr/ltms/temp/241.png", 720, 630)
//            )
//
//            given(replyMock.message.messageButton).willReturn(
//                    MessageButtonVO(
//                            "상세정보",
//                            "http://u-campus.ajou.ac.kr/ltms/temp/241.png"
//                    )
//            )


            // given
            given(replyMock.message).willReturn(
                    // message의 text는 매 크롤링마다 가변적이므로 검증하지 않는다.
                    MessageVO("",
                            PhotoVO("http://u-campus.ajou.ac.kr/ltms/temp/241.png", 720, 630),
                            MessageButtonVO(
                                    "상세정보",
                                    "http://u-campus.ajou.ac.kr/ltms/temp/241.png"
                            )
                    )
            )

            given(replyMock.keyboard).willReturn(
                    KeyboardVO(
                            "buttons",
                            listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
                    )
            )

            // when
            val replyVO = repo.findVacancyReply("C1")

            // then
            assertEquals(replyVO.keyboard, replyMock.keyboard)
            assertEquals(replyVO.message.photo, replyMock.message.photo)
            assertEquals(replyVO.message.messageButton, replyMock.message.messageButton)
        }
    }

}
