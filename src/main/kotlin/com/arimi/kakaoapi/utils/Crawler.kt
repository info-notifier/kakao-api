package com.arimi.kakaoapi.utils

import org.jsoup.Jsoup

class Crawler {
    object Vacancy {
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

    object FoodCourt {
        val getFnOf: HashMap<String, () -> String> = hashMapOf (
                "student" to this::getStudentText,
                "dormitory" to this::getDormitoryText,
                "faculty" to this::getFacultyText
        )

        private fun getStudentText(): String {
            val url = "http://www.ajou.ac.kr/main/life/food.jsp"
            var retText = ""
            var hasMenu = false

            Jsoup.connect(url).get().run {
                this.select("table.ajou_table")[0].select("tr").forEach {tr ->
                    tr.children().forEach { el ->
                        // Build Menu header
                        if (el.hasText() && !el.hasClass("no_right left_pd") && el.tagName() != "th") {
                            val menuHeader = el.text()
                            retText += "[${menuHeader.trim()}]\n"
                        // Build Menu
                        } else {
                            val menu = el.children().select("li").eachText().joinToString("\n")
                            if (menu.trim() != "") hasMenu = true
                            retText += "${menu.trim()}\n\n"
                        }
                    }
                }
            }
            return if (hasMenu) retText.trim() else ""
        }

        private fun getDormitoryText(): String {
            return ""
        }

        private fun getFacultyText(): String {
            return ""
        }
    }
}