package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.context.SecurityContextHolder
import ru.dmitriyt.springboilerplate.dto.enums.ApiError
import ru.dmitriyt.springboilerplate.dto.enums.getException
import ru.dmitriyt.springboilerplate.entity.TokenPairEntity

interface TokenPairRepository : JpaRepository<TokenPairEntity, Long> {
    fun findAllByDeviceId(deviceId: String): List<TokenPairEntity>
    fun findByDeviceIdAndRefreshToken(deviceId: String, refreshToken: String): TokenPairEntity?
    fun findByAccessTokenAndIsActiveIsTrue(accessToken: String): TokenPairEntity?
}