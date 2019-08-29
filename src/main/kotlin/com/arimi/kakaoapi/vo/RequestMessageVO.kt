package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RequestMessageVO(
        val type: String,
        val content: String
) {
    // TODO: hashcode도 같이 override 하기
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is RequestMessageVO)
            false
        else
            other.type == type && other.content == content
    }
}