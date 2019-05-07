package com.arimi.kakaoapi

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

// 싱글톤 패턴 적용
class Crawler {
    companion object {
        private val options = ChromeOptions().addArguments("headless")
        private val driver = ChromeDriver(options)
    }

    object FoodCourt {
        private val ajouTables: List<WebElement>
        private val foodCourtsHtml: HashMap<String, WebElement>
        private val keyMapper = hashMapOf (
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
                        keyMapper[timeBuf]
                        // tr 자식 중 class attribute가 없는 td -> time (아침, 점심, 저녁)
                        it.findElements(By.cssSelector("td:not([class])")).map {
                            time -> timeBuf = time.text?: ""
                            if (time.text != "") dormFoodMap[keyMapper[timeBuf]] = ""
                        }

                        // tr 자식 중 class가 no_right left_pd인 td -> menu
                        it.findElements(By.cssSelector("td.no_right.left_pd")).map {
                            menu -> if (menu.text != "") dormFoodMap[keyMapper[timeBuf]] = menu.text?: ""
                        }
                    }

            dormFoodMap.forEach { (k, v) ->
                println("key: $k")
                println("value:")
                println(v)
            }
        }
    }

    object Vacancy {
        fun c1(): String? {
            return ""
        }

        fun d1(): String? {
            return ""
        }
    }
}