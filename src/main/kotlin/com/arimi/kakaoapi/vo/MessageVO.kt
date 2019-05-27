package com.arimi.kakaoapi.vo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class MessageVO (
        val text: String,
        val photo: PhotoVO? = null,
        val messageButton: MessageButtonVO? = null
) {
    constructor(text: String, messageButton: MessageButtonVO) : this(text, null, messageButton)

    constructor(text: String, photo: PhotoVO) : this(text, photo, null)

    constructor(text: String) : this(text, null)
}