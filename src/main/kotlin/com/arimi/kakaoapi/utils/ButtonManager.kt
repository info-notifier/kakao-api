package com.arimi.kakaoapi.utils

import com.arimi.kakaoapi.dao.MenuNameMapperDAO
import com.arimi.kakaoapi.dao.RedisDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ButtonManager @Autowired constructor(
        private val mapperDAO: MenuNameMapperDAO,
        private val redisDAO: RedisDAO
) {
    // 해당 식당에 내용이 있으면 filteredButtons에 버튼을 추가하고 내용이 없으면 버튼을 추가핳지 않는다.
    fun getFoodCourtFilteredButton(foodCourtButtons: List<String>): List<String> {
        val filteredButtons = mutableListOf<String>()

        foodCourtButtons.forEach { button ->
            val menu = mapperDAO.getMenuName(button)
            if(redisDAO.getScrappedText(menu) != "") {
                filteredButtons.add(button)
            }
        }

        filteredButtons.add("처음으로")
        return filteredButtons
    }
}