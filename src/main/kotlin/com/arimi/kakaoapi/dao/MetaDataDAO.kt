package com.arimi.kakaoapi.dao

import com.arimi.kakaoapi.vo.MetaDataForResponseVO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class MetaDataDAO @Autowired constructor(
        private val mapper: ObjectMapper
) {
    fun getMetaData(key: String): MetaDataForResponseVO {
//        Executable jar로 배포를 하기 위해서 file 대신 stream 처리
//        val metaDataJsonFile = ClassPathResource("metadata.json").file
        val metaDataJsonInputStream = ClassPathResource("metadata.json").inputStream
        val map = mapper.readValue<Map<String, MetaDataForResponseVO>>(metaDataJsonInputStream)

        return map.getValue(key)
    }
}