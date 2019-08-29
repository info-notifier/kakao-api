package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class ReplyVO (
        val message: MessageVO,
        val keyboard: KeyboardVO
) {
    // TODO: hashcode도 같이 override 하기
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is ReplyVO)
            false
        else
            other.message == message && other.keyboard == keyboard
    }
}