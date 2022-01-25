package ru.dmitriyt.springboilerplate.dto.auth

import ru.dmitriyt.springboilerplate.entity.TokenPairEntity

class Token(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
) {
    companion object {
        fun fromEntity(tokenPairEntity: TokenPairEntity, expiresIn: Long): Token {
            return Token(
                accessToken = tokenPairEntity.accessToken,
                refreshToken = tokenPairEntity.refreshToken,
                expiresIn = expiresIn,
            )
        }
    }
}