package ru.dmitriyt.springboilerplate.dto.model

import ru.dmitriyt.springboilerplate.exception.InternalApiException

class ErrorDTO(
    val code: String,
    val message: String,
    val tail: String? = null,
) {
    constructor(e: InternalApiException) : this(e.code, e.message, e.cause?.message)
}