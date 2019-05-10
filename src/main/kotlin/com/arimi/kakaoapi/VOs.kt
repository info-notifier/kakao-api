package com.arimi.kakaoapi

data class RequestMessageVO(val type: String, val content: String)

data class PhotoVO (val url: String, val width: Int, val height: Int)
data class MessageButtonVO (val label: String, val url: String)
data class KeyboardVO (val type: String, val buttons: List<String>)
data class MessageVO (val text: String, val photo: PhotoVO, val message_button: MessageButtonVO)
data class ResponseVO (val message: MessageVO, val keyboard: KeyboardVO)