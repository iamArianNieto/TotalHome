package com.example.totalhome.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.totalhome.models.User


class SessionManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUserSession(user: User) {
        with(sharedPref.edit()) {
            putBoolean("is_logged_in", true)
            putInt("user_id", user.id)
            putString("username", user.username)
            putString("full_name", user.fullName)
            putString("user_type", user.userType)
            putInt("fraccionamiento_id", user.fraccionamientoId)
            putString("fraccionamiento_name", user.fraccionamientoName)
            apply()
        }
    }

    fun getCurrentUser(): User? {
        return if (isLoggedIn()) {
            User(
                id = sharedPref.getInt("user_id", 0),
                username = sharedPref.getString("username", "") ?: "",
                fullName = sharedPref.getString("full_name", "") ?: "",
                userType = sharedPref.getString("user_type", "") ?: "",
                fraccionamientoId = sharedPref.getInt("fraccionamiento_id", 0),
                fraccionamientoName = sharedPref.getString("fraccionamiento_name", "") ?: ""
            )
        } else null
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("is_logged_in", false)
    }

    fun logout() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}