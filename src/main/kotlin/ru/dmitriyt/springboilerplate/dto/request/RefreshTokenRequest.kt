package ru.dmitriyt.springboilerplate.dto.request

class RefreshTokenRequest(
    val refreshToken: String,
    val deviceId: String,
)