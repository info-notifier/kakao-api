package com.arimi.kakaoapi.libs.scrapers

import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class VacancyScraper {
    fun getTextOf(place: String): String {
        val url = "http://u-campus.ajou.ac.kr/ltms/rmstatus/vew.rmstatus?bd_code=JL&rm_code=JL0$place"
        var textList: List<String>
        var retText: String
        Jsoup.connect(url).get().run {
            textList = this.select("td[valign=\"middle\"]")[1]
                    .allElements.eachText()[0].split(" ")
                    .map { it.trim() }
            retText = "◆ $place 열람실의 이용 현황\n" +
                    "  * 남은 자리: ${textList[6]}\n" +
                    "  * 사용률: ${textList[8].toInt() - textList[6].toInt()} / ${textList[8]} (${textList[12]})"
        }
        return retText
    }
}