package ru.dmitriyt.springboilerplate.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.dmitriyt.springboilerplate.config.property.JwtSettings
import ru.dmitriyt.springboilerplate.dto.model.Token
import ru.dmitriyt.springboilerplate.dto.enums.ApiError
import ru.dmitriyt.springboilerplate.dto.enums.Os
import ru.dmitriyt.springboilerplate.dto.enums.getException
import ru.dmitriyt.springboilerplate.entity.TokenPairEntity
import ru.dmitriyt.springboilerplate.repository.TokenPairRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@Service
@EnableConfigurationProperties(JwtSettings::class)
class TokenService @Autowired constructor(
    private val repository: TokenPairRepository,
    private val jwtSettings: JwtSettings,
) {
    @Transactional
    fun createToken(userId: Long, deviceId: String, isAnonym: Boolean, os: Os): Token {
        repository.findAllByDeviceId(deviceId)
            .filter { it.isActive }
            .map { entity ->
                entity.isActive = false
                repository.save(entity)
            }
        val token = buildToken(userId, deviceId, isAnonym, os)
        repository.save(token)
        return Token.fromEntity(token, jwtSettings.accessTokenExpirationTime)
    }

    fun refreshToken(deviceId: String, refreshToken: String): Token {
        val tokenPair = repository.findByDeviceIdAndRefreshToken(deviceId, refreshToken)
            ?: throw ApiError.WRONG_TOKEN.getException()
        if (!tokenPair.isActive || tokenPair.isExpired()) {
            throw ApiError.WRONG_TOKEN.getException()
        }
        tokenPair.updatedAt = LocalDateTime.now()
        tokenPair.accessToken = createAccessToken(deviceId)
        tokenPair.refreshToken = createRefreshToken(deviceId)
        return Token.fromEntity(repository.save(tokenPair), jwtSettings.accessTokenExpirationTime)
    }

    fun getCurrentTokenPair(): TokenPairEntity {
        return repository.findTokenPair()
    }

    private fun buildToken(userId: Long, deviceId: String, isAnonym: Boolean, os: Os): TokenPairEntity {
        return TokenPairEntity(
            accessToken = createAccessToken(deviceId),
            refreshToken = createRefreshToken(deviceId),
            isAnonym = isAnonym,
            deviceId = deviceId,
            os = os,
            userId = userId,
        )
    }

    private fun createAccessToken(deviceId: String): String {
        return createToken(deviceId, jwtSettings.accessTokenExpirationTime)
    }

    private fun createRefreshToken(deviceId: String): String {
        return createToken(deviceId, jwtSettings.refreshTokenExpirationTime)
    }

    private fun createToken(deviceId: String, expiresIn: Long): String {
        val expireDate = LocalDateTime.now()
            .plusSeconds(expiresIn)
            .toInstant(ZoneOffset.UTC)
            .let { Date.from(it) }
        return Jwts.builder()
            .setSubject(deviceId)
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, jwtSettings.secret)
            .compact()
    }

    private fun getDeviceIdFromAccessToken(accessToken: String): String {
        return Jwts.parser().setSigningKey(jwtSettings.secret).parseClaimsJws(accessToken).body.subject
    }

    private fun TokenPairEntity.isExpired(): Boolean {
        return updatedAt.plusDays(jwtSettings.refreshTokenExpirationTime).isBefore(LocalDateTime.now())
    }
}