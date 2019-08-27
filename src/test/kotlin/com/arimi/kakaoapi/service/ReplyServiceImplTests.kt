package com.arimi.kakaoapi.service

import com.arimi.kakaoapi.exception.WrongRequestException
import com.arimi.kakaoapi.repository.ReplyRepositoryImpl
import com.arimi.kakaoapi.service.ReplyServiceImpl
import com.arimi.kakaoapi.vo.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReplyServiceImplTests {
    @Mock
    lateinit var res: ReplyVO

    @Mock
    lateinit var repo: ReplyRepositoryImpl

    @InjectMocks
    lateinit var service: ReplyServiceImpl

    @BeforeEach
    fun resetRepository() = reset(repo)

    @Nested
    inner class FoodCourtTest {
        @Test
        fun student() {
            given(repo.findFoodCourtReply("student")).willReturn(res)

            service.getReplyByContent("학생식당")

            verify(repo, atLeastOnce()).findFoodCourtReply("student")
        }

        @Test
        fun dormitory() {
            given(repo.findFoodCourtReply("dormitory")).willReturn(res)

            service.getReplyByContent("기숙사식당")

            verify(repo, atLeastOnce()).findFoodCourtReply("dormitory")
        }

        @Test
        fun faculty() {
            given(repo.findFoodCourtReply("faculty")).willReturn(res)

            service.getReplyByContent("교직원식당")

            verify(repo, atLeastOnce()).findFoodCourtReply("faculty")
        }
    }

    @Nested
    inner class VacancyTest {
        @Test
        fun c1() {
            // given
            given(repo.findVacancyReply("C1")).willReturn(res)

            // when
            service.getReplyByContent("C1 열람실")

            // then
            verify(repo, atLeastOnce()).findVacancyReply("C1")
        }

        @Test
        fun d1() {
            // given
            given(repo.findVacancyReply("D1")).willReturn(res)

            // when
            service.getReplyByContent("D1 열람실")

            // then
            verify(repo, atLeastOnce()).findVacancyReply("D1")
        }

        @Test
        fun plug() {
            // given
            given(repo.findPlugReply()).willReturn(res)

            // when
            service.getReplyByContent("C1 열람실 플러그 위치")

            // then
            verify(repo, atLeastOnce()).findPlugReply()
        }
    }

    @Nested
    inner class WrongRequestTest {
        @Test
        fun exception() {
            try {
                service.getReplyByContent("content from wrong request")
            } catch (e: WrongRequestException) {
                assertEquals(e.message, "BAD REQUEST")
            }
        }
    }
}