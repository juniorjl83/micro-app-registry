package com.example.identity

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {
        // TODO: validate, hash password, store user, emit audit event
        return ResponseEntity.ok(mapOf("status" to "registered"))
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        // TODO: authenticate, issue JWT, refresh token, emit audit event
        return ResponseEntity.ok(mapOf("accessToken" to "<jwt>", "refreshToken" to "<refresh>"))
    }
}

data class RegisterRequest(val email: String, val password: String, val name: String)

data class LoginRequest(val email: String, val password: String)
