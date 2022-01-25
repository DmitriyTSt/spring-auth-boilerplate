package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.context.SecurityContextHolder
import ru.dmitriyt.springboilerplate.dto.enums.ApiError
import ru.dmitriyt.springboilerplate.dto.enums.getException
import ru.dmitriyt.springboilerplate.entity.TokenPairEntity

interface TokenPairRepository : JpaRepository<TokenPairEntity, Long> {
    fun findAllByDeviceId(deviceId: String): List<TokenPairEntity>
    fun findByDeviceIdAndRefreshToken(deviceId: String, refreshToken: String): TokenPairEntity?
    fun findByAccessTokenAndActiveIsTrue(accessToken: String): TokenPairEntity?
    fun findTokenPair(): TokenPairEntity {
        try {
            val token = SecurityContextHolder.getContext().authentication.principal as TokenPairEntity

            return findByAccessTokenAndActiveIsTrue(token.accessToken)
                ?: throw ApiError.ACCESS_TOKEN_NOT_FOUND.getException()
        } catch (e: Exception) {
            throw ApiError.ACCESS_TOKEN_NOT_FOUND.getException(e)
        }
    }
}