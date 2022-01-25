package ru.dmitriyt.springboilerplate.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtSettings {
    var secret: String = ""
    var accessTokenExpirationTime: Long = 0
    var refreshTokenExpirationTime: Long = 0
}