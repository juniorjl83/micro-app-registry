package com.example.identity

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder : PasswordEncoder {
    private val delegate = BCryptPasswordEncoder()

    override fun encode(rawPassword: String): String = delegate.encode(rawPassword)
    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        delegate.matches(rawPassword, encodedPassword)
}