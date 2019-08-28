package com.arimi.kakaoapi.dao

class VacancyDAO {
    // TODO: metadata -> vo로 빼야하나?
    data class MetaData(val type: String, val imgUrl: String?, val buttons: List<String>)

    companion object {
        val getMetaDataFor: HashMap<String, MetaData> = hashMapOf (
                "C1" to MetaData (
                        "buttons",
                        "http://u-campus.ajou.ac.kr/ltms/temp/241.png",
                        listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
                ),
                "D1" to MetaData (
                        "buttons",
                        "http://u-campus.ajou.ac.kr/ltms/temp/261.png",
                        listOf("C1 열람실", "D1 열람실", "처음으로")
                ),
                "plug" to MetaData (
                        "buttons",
                        "https://user-images.githubusercontent.com/31656287/57567720-87545980-7418-11e9-96be-d6f3aaca74b6.png",
                        listOf("C1 열람실", "D1 열람실", "처음으로")
                )
        )
    }
}