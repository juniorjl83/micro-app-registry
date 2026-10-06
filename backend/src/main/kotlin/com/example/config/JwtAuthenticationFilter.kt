package com.example.config

import com.example.identity.JwtService
import com.example.identity.User
import com.example.identity.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.Key
import java.util.Base64
import java.util.Collections

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override protected fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)
            try {
                // Recreate the signing key from the secret stored in environment
                val secretBase64 = System.getenv("jwt.secret")
                if (secretBase64 == null || secretBase64.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JWT secret not configured")
                    return
                }
                val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretBase64))

                val claims: Claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJwt(token)
                    .getBody()

                val userId = claims.getSubject().toLongOrNull()
                if (userId == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token subject")
                    return
                }
                val user = userRepository.findById(userId) ?:
                        throw IllegalArgumentException("User not found for id: $userId")

                val authentication = UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList() // authorities can be added later
                )
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token: ${e.message}")
                return
            }
        }
        filterChain.doFilter(request, response)
    }
}