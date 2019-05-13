package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class MessageVO (val text: String) {
    lateinit var photo: PhotoVO
    lateinit var messageButton: MessageButtonVO

    constructor (text: String, photo: PhotoVO): this(text) {
        this.photo = photo
    }

    constructor (text: String, messageButton: MessageButtonVO): this(text) {
        this.messageButton = messageButton
    }

    constructor (text: String, photo: PhotoVO, messageButton: MessageButtonVO): this(text, photo) {
        this.messageButton = messageButton
    }
}