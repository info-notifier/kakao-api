package com.arimi.kakaoapi

import com.arimi.kakaoapi.exception.WrongRequestException
import com.arimi.kakaoapi.repository.VacancyMessageRepository
import com.arimi.kakaoapi.service.MessageServiceImpl
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
class MessageServiceImplTests {
    @Mock
    lateinit var res: ResponseVO

    @Mock
    lateinit var repo: VacancyMessageRepository

    @InjectMocks
    lateinit var service: MessageServiceImpl

    @BeforeEach
    fun resetRepository() = reset(repo)

    @Nested
    inner class VacancyTest {
        @Test
        fun c1 () {
            // given
            given(repo.getMessage("C1")).willReturn(res)

            // when
            service.getMessage("C1 열람실")

            // then
            verify(repo, atLeastOnce()).getMessage("C1")
        }

        @Test
        fun d1 () {
            // given
            given(repo.getMessage("D1")).willReturn(res)

            // when
            service.getMessage("D1 열람실")

            // then
            verify(repo, atLeastOnce()).getMessage("D1")
        }

        @Test
        fun plug () {
            // given
            given(repo.getPlugMessage()).willReturn(res)

            // when
            service.getMessage("C1 열람실 플러그 위치")

            // then
            verify(repo, atLeastOnce()).getPlugMessage()
        }
    }

    @Nested
    inner class WrongRequestTest {
        @Test
        fun exception() {
            try {
                service.getMessage("content from wrong request")
            } catch (e: WrongRequestException) {
                assertEquals(e.message, "BAD REQUEST")
            }
        }
    }
}