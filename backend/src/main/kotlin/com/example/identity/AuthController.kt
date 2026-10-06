package com.example.identity

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap
import java.util.Map

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    data class RegisterRequest(
        val email: String,
        val password: String,
        val name: String
    )
    data class LoginRequest(
        val email: String,
        val password: String
    )
    data class AuthResponse(
        val accessToken: String,
        val refreshToken: String   // placeholder – you can implement real refresh later
    )

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {
        // 1️⃣ Validate email not already used
        if (userRepository.findByEmail(req.email) != null) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Email already in use"))
        }

        // 2️⃣ Hash password and persist user
        val user = User(
            email = req.email,
            passwordHash = passwordEncoder.encode(req.password),
            name = req.name
        )
        userRepository.save(user)

        // 3️⃣ Return success (no token yet – client must login)
        return ResponseEntity.ok(mapOf("status" to "registered"))
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        // 1️⃣ Find user by email
        val user = userRepository.findByEmail(req.email) ?:
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Invalid credentials"))

        // 2️⃣ Verify password
        if (!passwordEncoder.matches(req.password, user.passwordHash)) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Invalid credentials"))
        }

        // 3️⃣ Create JWT
        val accessToken = jwtService.generateAccessToken(user.id)

        // 4️⃣ (Optional) generate a refresh token – here we just echo a placeholder
        val response = AuthResponse(
            accessToken = accessToken,
            refreshToken = ""   // implement real refresh‑token storage if you need it
        )
        return ResponseEntity.ok(response)
    }
}