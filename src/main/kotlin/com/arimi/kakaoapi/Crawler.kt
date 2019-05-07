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
        private var foodCourts: HashMap<String, WebElement?>

        init {
            driver.get("http://www.ajou.ac.kr/main/life/food.jsp")

            // driver.get 이후 실행되어야 하므로 init 블록 사용
            ajouTables = driver.findElements(By.className("ajou_table"))
            foodCourts = hashMapOf (
                    "studentFood" to ajouTables[0],
                    "dormFood" to ajouTables[1],
                    "facultyFood" to ajouTables[2]
            )
        }

        fun student(): String? {
            return foodCourts["studentFood"]?.text
        }
    }

    object VacancyOf {

    }
}