package com.example.identity

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Base64
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secretBase64: String,
    @Value("\${jwt.expiration-ms:3600000}") private val expirationMs: Long
) {
    private val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretBase64))

    fun generateAccessToken(userId: Long): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.getTime() + expirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}