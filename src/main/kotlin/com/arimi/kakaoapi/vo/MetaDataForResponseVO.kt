package com.arimi.kakaoapi.vo

// 생성자에 val type: String = "" 와 같이 default 값을 지정해줘야
// default constructor가 생성되고 jackson을 이용한 JSON(to Object) deserialization이 가능하다.
data class MetaDataForResponseVO(
        val type: String = "",
        val imgUrl: String? = null,
        val buttons: List<String> = listOf()
) {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is MetaDataForResponseVO)
            false
        else
            other.type == type && other.imgUrl == imgUrl && other.buttons == buttons
    }
}