package com.arimi.kakaoapi.vo

data class MetaDataForResponseVO(
        val type: String,
        val imgUrl: String?,
        val buttons: List<String>
) {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is MetaDataForResponseVO)
            false
        else
            other.type == type && other.imgUrl == imgUrl && other.buttons == buttons
    }
}