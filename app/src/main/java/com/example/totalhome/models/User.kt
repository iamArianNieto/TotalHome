package com.example.totalhome.models

data class User(
    val id: Int,
    val username: String,
    val fullName: String,
    val userType: String, // "resident", "admin", "security", "staff"
    val fraccionamientoId: Int,
    val fraccionamientoName: String
)