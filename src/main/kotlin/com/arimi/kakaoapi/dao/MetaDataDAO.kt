package com.arimi.kakaoapi.dao

import com.arimi.kakaoapi.vo.MetaDataForResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TestDAO @Autowired constructor(
        redisTemplate: RedisTemplate<String, Any>
) {
    private val opsForValue = redisTemplate.opsForValue()
    // TODO: 아래 데이터들 DB에 넣기
    private val getMetaDataFor: HashMap<String, MetaDataForResponseVO> = hashMapOf (
            "C1" to MetaDataForResponseVO(
                    "buttons",
                    "http://u-campus.ajou.ac.kr/ltms/temp/241.png",
                    listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
            ),
            "D1" to MetaDataForResponseVO(
                    "buttons",
                    "http://u-campus.ajou.ac.kr/ltms/temp/261.png",
                    listOf("C1 열람실", "D1 열람실", "처음으로")
            ),
            "plug" to MetaDataForResponseVO(
                    "buttons",
                    "https://user-images.githubusercontent.com/31656287/57567720-87545980-7418-11e9-96be-d6f3aaca74b6.png",
                    listOf("C1 열람실", "D1 열람실", "처음으로")
            ),
            "student" to MetaDataForResponseVO(
                    "buttons",
                    null,
                    listOf("기숙사식당", "교직원식당", "처음으로")
            ),
            "dormitory" to MetaDataForResponseVO(
                    "buttons",
                    null,
                    listOf("학생식당", "교직원식당", "처음으로")
            ),
            "faculty" to MetaDataForResponseVO(
                    "buttons",
                    null,
                    listOf("학생식당", "기숙사식당", "처음으로")
            )
    )

    fun addMetaData(key: String) {
        opsForValue.setIfAbsent(key, getMetaDataFor[key]!!)
    }

    fun getMetaData(key: String): MetaDataForResponseVO {
        addMetaData(key)
        println("밸류얌")
        println(opsForValue[key]!!)
        return opsForValue[key]!! as MetaDataForResponseVO
    }
}