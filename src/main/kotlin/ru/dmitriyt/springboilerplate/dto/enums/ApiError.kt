package ru.dmitriyt.springboilerplate.dto.enums

import org.springframework.http.HttpStatus
import ru.dmitriyt.springboilerplate.exception.InternalApiException

enum class ApiError(
    val message: String,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
) {
    UNKNOWN_ERROR("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_TOKEN("Неправильный токен, необходимо повторно авторизоваться"),

}

fun ApiError.getException() = InternalApiException(httpStatus, name, message)
fun ApiError.getException(e: Exception) = InternalApiException(httpStatus, name, message, e)