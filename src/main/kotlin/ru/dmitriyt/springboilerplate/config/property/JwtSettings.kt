package ru.dmitriyt.springboilerplate.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtSettings(
    val secret: String,
    val accessTokenExpirationTime: Long,
    val refreshTokenExpirationTime: Long,
)