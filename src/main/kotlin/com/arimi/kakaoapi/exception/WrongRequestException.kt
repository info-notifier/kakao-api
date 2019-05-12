package com.arimi.kakaoapi.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "잘못된 요청입니다. (BAD CONTENT)")
class WrongRequestException(message: String) : RuntimeException(message)