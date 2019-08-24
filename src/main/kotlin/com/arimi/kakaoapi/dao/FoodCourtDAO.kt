package com.arimi.kakaoapi.dao

class FoodCourtDAO {
    companion object {
        data class MetaData(val type: String, val imgUrl: String?, val buttons: List<String>)

        val getMetaDataFor: HashMap<String, MetaData> = hashMapOf (
                "student" to MetaData (
                        "buttons",
                        null,
                        listOf("기숙사식당", "교직원식당", "처음으로")
                ),
                "dormitory" to MetaData (
                        "buttons",
                        null,
                        listOf("학생식당", "교직원식당", "처음으로")
                ),
                "faculty" to MetaData (
                        "buttons",
                        null,
                        listOf("학생식당", "기숙사식당", "처음으로")
                )
        )
    }
}