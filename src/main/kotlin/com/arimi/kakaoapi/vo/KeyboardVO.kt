package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class KeyboardVO (
        val type: String,
        val buttons: List<String>
) {
    // TODO: hashcode도 같이 override 하기
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is KeyboardVO)
            false
        else
            other.type == type && other.buttons == buttons
    }
}