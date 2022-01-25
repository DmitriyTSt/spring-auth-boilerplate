package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.dmitriyt.springboilerplate.entity.TokenPairEntity

interface TokenPairRepository : JpaRepository<TokenPairEntity, Long> {
    fun findAllByDeviceId(deviceId: String): List<TokenPairEntity>
    fun findByDeviceIdAndRefreshToken(deviceId: String, refreshToken: String): TokenPairEntity?
}