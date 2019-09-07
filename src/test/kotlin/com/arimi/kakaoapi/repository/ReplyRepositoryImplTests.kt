package com.arimi.kakaoapi.repository

import com.arimi.kakaoapi.libs.scrapers.FoodCourtScraper
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
import org.mockito.Mockito
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
        private val spy = spy(VacancyScraper())
        private val c1PhotoUrl = "http://u-campus.ajou.ac.kr/ltms/temp/241.png"
        private val d1PhotoUrl = "http://u-campus.ajou.ac.kr/ltms/temp/261.png"
        private val plugPhotoUrl = "https://user-images.githubusercontent.com/31656287/57567720-87545980-7418-11e9-96be-d6f3aaca74b6.png"

        @Test
        fun c1() {
            /* given */
            given(crawler.vacancy).willReturn(spy)

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
            verify(crawler.vacancy, atLeastOnce()).getTextOf("C1")

            // replyVO의 요소를 제대로 불러왔는지 검증
            assertEquals(replyMock.keyboard, replyVO.keyboard)
            assertEquals(replyMock.message.photo, replyVO.message.photo)
            assertEquals(replyMock.message.messageButton, replyVO.message.messageButton)
        }

        @Test
        fun d1() {
            /* given */
            given(crawler.vacancy).willReturn(spy)
            // 여기서 spy를 사용하지 않고,
            // val foo = VacancyScraper()
            // given(crawler.foodCourt).willReturn(foo) 로 하면
            // verify(crawler.vacancy, atLeastOnce()).getTextOf("D1") 부분에서
            // verify의 첫 인자가 mock 객체가 아니라는 에러가 발생한다.
            // 즉 spy를 통해서 실제 객체를 mock 객체처럼 사용하는 것이다.

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
            verify(crawler.vacancy, atLeastOnce()).getTextOf("D1")

            // replyVO의 요소를 제대로 불러왔는지 검증
            assertEquals(replyMock.keyboard, replyVO.keyboard)
            assertEquals(replyMock.message.photo, replyVO.message.photo)
            assertEquals(replyMock.message.messageButton, replyVO.message.messageButton)
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

            assertEquals(replyMock.keyboard, replyVO.keyboard)
            assertEquals(replyMock.message.text, replyVO.message.text)
            assertEquals(replyMock.message.photo, replyVO.message.photo)
            assertEquals(replyMock.message.messageButton, replyVO.message.messageButton)
        }
    }

    @Nested
    inner class FoodCourtTest {
        private val spy = spy(FoodCourtScraper())
        private val studentText = "student text"
        private val dormitoryText = "dormitory text"
        private val facultyText = "faculty text"

        @Test
        fun student() {
            /* spy 사용 이유 */
            // crawler.foodCourt에 대한 mocking이 필요한데
            // 이 때 spy를 통해서 실제 객체를 주입하는 편이 가장 편리하다.
            // spy 이용 시, 실제 객체를 mockito에 mock 객체로 인식시킬 수 있다.
            // 따라서 mock 객체가 아니라서 발생할 수 있는 에러를 방지 할 수 있다.

            /* given */
            given(crawler.foodCourt).willReturn(spy)
            doReturn(studentText).`when`(spy).getTextOf("student")
//            given(crawler.foodCourt.getTextOf("student")).willReturn("asdf")

            /* when */
            val replyVO = repo.findFoodCourtReply("student")

            /* then */
            verify(crawler.foodCourt, atLeastOnce()).getTextOf("student")
            assertEquals(studentText, replyVO.message.text)
        }

        @Test
        fun dormitory() {
            val spy = spy(FoodCourtScraper())

            /* given */
            given(crawler.foodCourt).willReturn(spy)
            doReturn(dormitoryText).`when`(spy).getTextOf("dormitory")
//            given(crawler.foodCourt.getTextOf("dormitory")).willReturn("asdf")

            /* when */
            val replyVO = repo.findFoodCourtReply("dormitory")

            /* then */
            verify(crawler.foodCourt, atLeastOnce()).getTextOf("dormitory")
            assertEquals(dormitoryText, replyVO.message.text)
        }

        @Test
        fun faculty() {
            val spy = spy(FoodCourtScraper())

            /* given */
            given(crawler.foodCourt).willReturn(spy)
            doReturn(facultyText).`when`(spy).getTextOf("faculty")
//            given(crawler.foodCourt.getTextOf("faculty")).willReturn("asdf")

            /* when */
            val replyVO = repo.findFoodCourtReply("faculty")

            /* then */
            verify(crawler.foodCourt, atLeastOnce()).getTextOf("faculty")
            assertEquals(facultyText, replyVO.message.text)
        }
    }
}
