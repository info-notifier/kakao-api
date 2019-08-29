package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
// TODO: 상속 때문에 open class로 바꿔야하는지 알아보기 (mokito test시 문제 생길 수 있음)
data class PhotoVO (
        val url: String,
        val width: Int,
        val height: Int
) {
    private fun getPureUrl(url: String): String = url.split("?")[0]

    // TODO: hashcode도 같이 override 하기
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is PhotoVO) {
            false
        } else {
            other.getPureUrl(other.url) == getPureUrl(url)
                    && other.width == width
                    && other.height == height
        }
    }
}