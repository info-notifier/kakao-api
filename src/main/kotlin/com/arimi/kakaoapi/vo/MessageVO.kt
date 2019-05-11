package com.arimi.kakaoapi.vo

data class MessageVO (
        val text: String,
        val photo: PhotoVO? = null,
        val messageButton: MessageButtonVO? = null
)