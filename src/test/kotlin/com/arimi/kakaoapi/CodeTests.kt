package com.arimi.kakaoapi

import com.arimi.kakaoapi.dao.MetaDataDAO
import com.arimi.kakaoapi.vo.MetaDataForResponseVO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest
class JsonTests {
    @Test
    fun jsonTest(@Autowired metaDAO: MetaDataDAO) {
        val cp = ClassPathResource("metadata.json")
        val bar = MetaDataForResponseVO()
//        mapper.readValue(File("resources/metadata.json"), MetaDataForResponseVO::class.java)
        val data = metaDAO.getMetaData("C1")


        println(data)


    }
}