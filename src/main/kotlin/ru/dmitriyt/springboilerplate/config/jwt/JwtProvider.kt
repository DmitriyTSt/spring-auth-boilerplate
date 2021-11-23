package ru.dmitriyt.springboilerplate.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Component
class JwtProvider {

    @Value("$(jwt.secret)")
    private lateinit var jwtSecret: String

    fun generateToken(login: String): String {
        val expirationDate = LocalDate.now()
            .plusDays(15)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .let { Date.from(it) }
        return Jwts.builder()
            .setSubject(login)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getLoginFromToken(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }
}