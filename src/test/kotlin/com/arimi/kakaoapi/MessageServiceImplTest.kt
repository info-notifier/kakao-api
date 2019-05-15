package com.arimi.kakaoapi

import com.arimi.kakaoapi.repository.VacancyMessageRepository
import com.arimi.kakaoapi.service.MessageServiceImpl
import com.arimi.kakaoapi.vo.*
import com.arimi.kakaoapi.dao.VacancyDAO
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.*
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
class MessageServiceImplTest {
    @Mock
    lateinit var repo: VacancyMessageRepository

    @InjectMocks
    lateinit var service: MessageServiceImpl

    @Nested
    inner class VacancyTest {
        @Test
        fun c1 () {
            val imgUrl = VacancyDAO.getMetaDataFor["C1"]?.imgUrl
            val buttons = VacancyDAO.getMetaDataFor["C1"]?.buttons
            val photo = PhotoVO(imgUrl!!, 720, 630)
            val msgBtn = MessageButtonVO("상세정보", imgUrl)
            val message = MessageVO("crawled data", photo, msgBtn)
            val keyboard = KeyboardVO("buttons", buttons!!)

            val res = ResponseVO(message, keyboard)


            // given
            given(repo.getMessage("C1")).willReturn(res)

            // when
            val response = service.getMessage("C1 열람실")

            // then
            verify(repo, atLeastOnce()).getMessage(anyString())
            assertEquals(res.message.photo.height, response.message.photo.height)
        }

        @Test
        fun d1 () {
            val imgUrl = VacancyDAO.getMetaDataFor["D1"]?.imgUrl
            val buttons = VacancyDAO.getMetaDataFor["D1"]?.buttons
            val photo = PhotoVO(imgUrl!!, 720, 630)
            val msgBtn = MessageButtonVO("상세정보", imgUrl)
            val message = MessageVO("crawled data", photo, msgBtn)
            val keyboard = KeyboardVO("buttons", buttons!!)

            val res = ResponseVO(message, keyboard)


            // given
            given(repo.getMessage("D1")).willReturn(res)

            // when
            val response = service.getMessage("D1 열람실")

            // then
            verify(repo, atLeastOnce()).getMessage(anyString())
            assertEquals(res.message.photo.height, response.message.photo.height)
        }
    }
}