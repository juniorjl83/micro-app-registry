package com.example.identity

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder(private val delegate: BCryptPasswordEncoder = BCryptPasswordEncoder()) : PasswordEncoder {
    override fun encode(rawPassword: CharSequence): String = delegate.encode(rawPassword)
    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean = delegate.matches(rawPassword, encodedPassword)
}