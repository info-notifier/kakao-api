package com.arimi.kakaoapi.libs.scrapers

import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class FoodCourtScraper {
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