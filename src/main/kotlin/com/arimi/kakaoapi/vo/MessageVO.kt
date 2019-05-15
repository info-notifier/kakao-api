package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class MessageVO (private val _text: String) {
    private lateinit var _photo: PhotoVO
    private lateinit var _messageButton: MessageButtonVO

    constructor (text: String, photo: PhotoVO): this(text) {
        this._photo = photo
    }

    constructor (text: String, messageButton: MessageButtonVO): this(text) {
        this._messageButton = messageButton
    }

    constructor (text: String, photo: PhotoVO, messageButton: MessageButtonVO): this(text, photo) {
        this._messageButton = messageButton
    }

    val text: String get() = _text
    val photo: PhotoVO get() = _photo
    val messageButton: MessageButtonVO get() = _messageButton
}