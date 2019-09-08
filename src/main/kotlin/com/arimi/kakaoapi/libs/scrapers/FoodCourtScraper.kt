package com.arimi.kakaoapi.libs.scrapers

import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class FoodCourtScraper {
    private val url = "http://www.ajou.ac.kr/main/life/food.jsp"
    private val getFnOf: HashMap<String, () -> String> = hashMapOf (
            "student" to ::getStudentText,
            "dormitory" to ::getDormitoryText,
            "faculty" to ::getFacultyText
    )

    fun getTextOf(place: String) = getFnOf[place]!!.invoke()

    // TODO: crontab 등 사용하여 db 혹은 파일시스템에 크롤링 내용 미리 캐싱해두기
    // 세 메소드의 차이는 table.ajou_table의 인덱스밖에 없다.
    // 하지만 향후 확장성 및 발생할 수 있는 html 문서 변경에 대처하기 위해서 분리해 둔다.
    private fun getStudentText(): String {
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
        var retText = ""
        var hasMenu = false

        // table.ajou_table > tr(s) > th or td ...
        Jsoup.connect(url).get().run {
            this.select("table.ajou_table")[1].select("tr").forEach {tr ->
                // el == th or td
                tr.children().forEach { el ->
                    // Build Menu header
                    // el이 text가 없고 no_right left_pd 클래스가 없고 th 태그가 아니면 그것은 header(점심/저녁/스낵 등)이다
                    if (el.hasText() && !el.hasClass("no_right left_pd") && el.tagName() != "th") {
                        val menuHeader = el.text()
                        retText += "[${menuHeader.trim()}]\n"
                    // Build Menu
                    // 아닌 경우 그것은 본문(메뉴)이다.
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

    private fun getFacultyText(): String {
        var retText = ""
        var hasMenu = false

        Jsoup.connect(url).get().run {
            this.select("table.ajou_table")[2].select("tr").forEach {tr ->
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
}