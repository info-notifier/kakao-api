package com.arimi.kakaoapi.dao

import com.arimi.kakaoapi.vo.MetaDataForResponseVO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class MenuMapperDAO @Autowired constructor(
        private val mapper: ObjectMapper
) {
    fun getMetaData(key: String): MetaDataForResponseVO {
        val metaDataJsonInputStream = ClassPathResource("metadata.json").inputStream
        val map = mapper.readValue<Map<String, MetaDataForResponseVO>>(metaDataJsonInputStream)

        return map.getValue(key)
    }
}