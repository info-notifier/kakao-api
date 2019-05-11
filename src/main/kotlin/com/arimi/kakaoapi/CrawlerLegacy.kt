package com.arimi.kakaoapi

import com.arimi.kakaoapi.vo.*
import org.jsoup.Jsoup
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.sql.Timestamp

// 싱글톤 패턴 적용
class CrawlerLegacy {
    companion object {
        private val options = ChromeOptions().addArguments("headless")
        private val driver = ChromeDriver(options)

        lateinit var message: MessageVO
        lateinit var keyboard: KeyboardVO
        lateinit var msgBtn: MessageButtonVO
        lateinit var photo: PhotoVO
    }

    // Using Selenium
    object FoodCourt {
        private val foodCourtsHtml: HashMap<String, WebElement>
        private val ajouTables: List<WebElement>
        private val convKorToEn = hashMapOf (
                "아침" to "breakfast",
                "점심" to "lunch",
                "저녁" to "dinner",
                "분식" to "snack"
        )
        init {
            driver.get("http://www.ajou.ac.kr/main/life/food.jsp")

            // driver.get 이후 실행되어야 하므로 init 블록 사용
            ajouTables = driver.findElements(By.className("ajou_table"))
            foodCourtsHtml = hashMapOf (
                    "student" to ajouTables[0],
                    "dorm" to ajouTables[1],
                    "faculty" to ajouTables[2]
            )
        }

        fun of(type: String) {
            val dormFoodMap = HashMap<String?, String>()
            foodCourtsHtml[type]?.findElements(By.tagName("tr"))
                    ?.map {
                        var timeBuf = ""
                        // tr 자식 중 class attribute가 없는 td -> time (아침, 점심, 저녁)
                        it.findElements(By.cssSelector("td:not([class])")).map {
                            time -> timeBuf = time.text?: ""
                            if (time.text != "") dormFoodMap[convKorToEn[timeBuf]] = ""
                        }

                        // tr 자식 중 class가 no_right left_pd인 td -> menu
                        it.findElements(By.cssSelector("td.no_right.left_pd")).map {
                            menu -> if (menu.text != "") dormFoodMap[convKorToEn[timeBuf]] = menu.text?: ""
                        }
                    }

            dormFoodMap.forEach { (k, v) ->
                println("key: $k")
                println("value:")
                println(v)
            }
        }
    }

    // Using JSoup
    object Vacancy {
        private data class MetaData(val url: String, val buttons: List<String>)
        private val getMetaDataFor: HashMap<String, MetaData> = hashMapOf (
                "C1" to MetaData (
                        "http://u-campus.ajou.ac.kr/ltms/temp/241.png",
                        listOf("D1 열람실", "C1 열람실", "C1 열람실 플러그 위치", "처음으로")
                ),
                "D1" to MetaData (
                        "http://u-campus.ajou.ac.kr/ltms/temp/261.png",
                        listOf("C1 열람실", "D1 열람실", "처음으로")
                )
        )

        private fun makeTextFor(place: String): String {
            val url = "http://u-campus.ajou.ac.kr/ltms/rmstatus/vew.rmstatus?bd_code=JL&rm_code=JL0$place"
            var textList: List<String>
            var retText: String
            Jsoup.connect(url).get().run {
                textList = select("td[valign=\"middle\"]")[1]
                        .allElements.eachText()[0].split(" ")
                        .map { it.trim() }
                retText = "◆ $place 열람실의 이용 현황\n" +
                        "  * 남은 자리: ${textList[6]}\n" +
                        "  * 사용률: ${textList[8].toInt() - textList[6].toInt()} / ${textList[8]} (${textList[12]})"
            }
            return retText
        }

        fun of(place: String): ResponseVO {
            val url = getMetaDataFor[place]?.url
            val buttons = getMetaDataFor[place]?.buttons
            val timestamp = Timestamp(System.currentTimeMillis())
            val text = makeTextFor(place)

            url?.let {
                photo = PhotoVO("$url?=${timestamp.time}", 720, 630)
                msgBtn = MessageButtonVO("상세정보", it)
                message = MessageVO(text, photo, msgBtn)
            }
            buttons?.let { keyboard = KeyboardVO("buttons", buttons) }

            // url, buttons가 null로 들어오는 경우 exception 처리 어떻게할까 뭐 그럴리는 없지만..
            // F1 열람실 같은 경우 C1처럼나오네
            return try {
                ResponseVO(message, keyboard)
            } catch (e: UninitializedPropertyAccessException) {
                println("here")
                throw e
            }
        }
    }
}