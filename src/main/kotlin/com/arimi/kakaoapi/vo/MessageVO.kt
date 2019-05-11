package com.arimi.kakaoapi.vo

data class MessageVO (
        val text: String,
        val messageButton: MessageButtonVO
) {
    constructor (
            text: String,
            messageButton: MessageButtonVO,
            photo: PhotoVO
    ): this(text, messageButton)
}