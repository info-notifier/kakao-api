package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.libs.scrapers.VacancyScraper
import com.arimi.kakaoapi.utils.Crawler
import com.arimi.kakaoapi.vo.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReplyRepositoryImplTests {
    @Mock
    lateinit var crawler: Crawler

    @Mock
    lateinit var replyMock: ReplyVO

    @InjectMocks
    lateinit var repo: ReplyRepositoryImpl

    @BeforeEach
    fun resetReplyMock() = reset(replyMock)

    @Nested
    inner class VacancyTest {
        private val c1PhotoUrl = "http://u-campus.ajou.ac.kr/ltms/temp/241.png"
        private val d1PhotoUrl = "http://u-campus.ajou.ac.kr/ltms/temp/261.png"
        private val plugPhotoUrl = "https://user-images.githubusercontent.com/31656287/57567720-87545980-7418-11e9-96be-d6f3aaca74b6.png"
        @Test
        fun c1() {
            /* given */
            given(crawler.vacancy).willReturn(VacancyScraper())

            given(replyMock.message).willReturn(
                    // message의 text는 매 크롤링마다 가변적이므로 검증하지 않는다.
                    MessageVO("",
                            PhotoVO(c1PhotoUrl, 720, 630),
                            MessageButtonVO("상세정보", c1PhotoUrl)
                    )
            )

            given(replyMock.keyboard).willReturn(
                    KeyboardVO(
                            "buttons",
                            listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
                    )
            )

            /* when */
            val replyVO = repo.findVacancyReply("C1")

            /* then */
            // crawler.vacancy 필드 실행여부 검증
            // TODO: vacancy("C1"), vacancy("D1") 처럼 구체적인 실행여부를 알 수 있는 방법은 없나?
            verify(crawler, atLeastOnce()).vacancy

            // replyVO의 요소를 제대로 불러왔는지 검증
            assertEquals(replyVO.keyboard, replyMock.keyboard)
            assertEquals(replyVO.message.photo, replyMock.message.photo)
            assertEquals(replyVO.message.messageButton, replyMock.message.messageButton)
        }

        @Test
        fun d1() {
            /* given */
            given(crawler.vacancy).willReturn(VacancyScraper())

            given(replyMock.message).willReturn(
                    // message의 text는 매 크롤링마다 가변적이므로 검증하지 않는다.
                    MessageVO("",
                            PhotoVO(d1PhotoUrl, 720, 630),
                            MessageButtonVO("상세정보", d1PhotoUrl)
                    )
            )

            given(replyMock.keyboard).willReturn(
                    KeyboardVO(
                            "buttons",
                            listOf("C1 열람실", "D1 열람실", "처음으로")
                    )
            )

            /* when */
            val replyVO = repo.findVacancyReply("D1")

            /* then */
            // crawler.vacancy 필드 실행여부 검증
            // TODO: vacancy("C1"), vacancy("D1") 처럼 구체적인 실행여부를 알 수 있는 방법은 없나?
            verify(crawler, atLeastOnce()).vacancy

            // replyVO의 요소를 제대로 불러왔는지 검증
            assertEquals(replyVO.keyboard, replyMock.keyboard)
            assertEquals(replyVO.message.photo, replyMock.message.photo)
            assertEquals(replyVO.message.messageButton, replyMock.message.messageButton)
        }

        @Test
        fun plug() {
            given(replyMock.message).willReturn(
                    MessageVO(
                            " * 플러그의 위치에 병아리가 있어요.\n" +
                                    "왼쪽 병아리부터 플러그와 가까운 자리 번호입니다.\n" +
                                    "(하단의 이미지는 실시간 이미지가 아닙니다.)\n" +
                                    "349, 380, 405 or 412, 444, 468, 473",
                            PhotoVO(plugPhotoUrl, 720, 200)
                    )
            )

            given(replyMock.keyboard).willReturn(
                    KeyboardVO(
                            "buttons",
                            listOf("C1 열람실", "D1 열람실", "처음으로")
                    )
            )

            val replyVO = repo.findPlugReply()

            assertEquals(replyVO.keyboard, replyMock.keyboard)
            assertEquals(replyVO.message.text, replyMock.message.text)
            assertEquals(replyVO.message.photo, replyMock.message.photo)
            assertEquals(replyVO.message.messageButton, replyMock.message.messageButton)
        }
    }
}
