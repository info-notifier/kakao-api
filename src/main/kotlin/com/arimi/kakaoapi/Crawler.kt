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

    object FoodCourtOf {
        private var ajouTables: List<WebElement>
        private var foodCourtsHtml: HashMap<String, WebElement?>
        init {
            driver.get("http://www.ajou.ac.kr/main/life/food.jsp")

            // driver.get 이후 실행되어야 하므로 init 블록 사용
            ajouTables = driver.findElements(By.className("ajou_table"))
            foodCourtsHtml = hashMapOf (
                    "studentFood" to ajouTables[0],
                    "dormFood" to ajouTables[1],
                    "facultyFood" to ajouTables[2]
            )
        }

        fun student(): String? {
//            foodCourtsHtml["studentFood"]?.findElement
            return ""
        }

        fun dorm() {
            val dormFoodMap = HashMap<String, String>()
            foodCourtsHtml["dormFood"]?.findElements(By.tagName("tr"))
                    ?.map {
                        var timeBuf = ""

                        // tr 자식 중 class attribute가 없는 td -> time (아침, 점심, 저녁)
                        it.findElements(By.cssSelector("td:not([class])")).map {
                            time -> timeBuf = time.text?: ""
                            if (time.text != "") dormFoodMap[timeBuf] = ""
                        }

                        // tr 자식 중 class가 no_right left_pd인 td -> menu
                        it.findElements(By.cssSelector("td.no_right.left_pd")).map {
                            menu -> if (menu.text != "") dormFoodMap[timeBuf] = menu.text?: ""
                        }
                    }

            dormFoodMap.forEach { (k, v) ->
                println("key: $k")
                println("value:")
                println(v)
            }
        }

        fun faculty() {

        }
    }

    object VacancyOf {
        fun c1(): String? {
            return ""
        }

        fun d1(): String? {
            return ""
        }
    }
}