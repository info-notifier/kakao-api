package com.arimi.kakaoapi

import com.arimi.kakaoapi.repository.VacancyMessageRepository
import com.arimi.kakaoapi.service.MessageServiceImpl
import com.arimi.kakaoapi.vo.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@SpringBootTest
class MessageServiceImplTest2 {
    @Mock
    lateinit var repo: VacancyMessageRepository

    @InjectMocks
    lateinit var service: MessageServiceImpl


    @BeforeAll
    fun buildVO() {
//        val imgUrl = repo.getMetaDataFor()
    }

    inner class VacancyTest {
        fun c1 () {
            val imgUrl = repo.getMetaDataFor["C1"]?.imgUrl
            val buttons = repo.getMetaDataFor["C1"]?.buttons
            val photo = PhotoVO(imgUrl!!, 720, 630)
            val msgBtn = MessageButtonVO("상세정보", imgUrl)
            val message = MessageVO("crawled data", photo, msgBtn)
            val keyboard = KeyboardVO("buttons", buttons!!)

            val res = ResponseVO(message, keyboard)


            // given
            given(repo.getMessage("C1"))
                    .willReturn(res)

            // when
            val a = service.getMessage("C1 열람실")

            // then
            verify(repo, atLeastOnce()).getMessage(anyString())
            
        }
    }
}