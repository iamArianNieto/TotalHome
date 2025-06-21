package com.example.totalhome.models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)