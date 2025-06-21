package com.example.totalhome.network

import com.example.totalhome.LoginResponse
import com.example.totalhome.RecoveryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

class ApiService {

    companion object {
        private const val BASE_URL = "https://tu-servidor.com/api/"

        suspend fun login(username: String, password: String): LoginResponse {
            return withContext(Dispatchers.IO) {
                try {
                    val url = URL("${BASE_URL}login")
                    val connection = url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val jsonBody = JSONObject().apply {
                        put("username", username)
                        put("password", password)
                    }

                    connection.outputStream.use { os ->
                        os.write(jsonBody.toString().toByteArray())
                    }

                    val responseCode = connection.responseCode
                    val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                        connection.inputStream.bufferedReader().readText()
                    } else {
                        connection.errorStream.bufferedReader().readText()
                    }

                    parseLoginResponse(response)

                } catch (e: Exception) {
                    LoginResponse(
                        success = false,
                        message = "Error de conexión: ${e.message}",
                        user = null
                    )
                }
            }
        }

        suspend fun recoverPassword(username: String): RecoveryResponse {
            return withContext(Dispatchers.IO) {
                try {
                    val url = URL("${BASE_URL}recover-password")
                    val connection = url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val jsonBody = JSONObject().apply {
                        put("username", username)
                    }

                    connection.outputStream.use { os ->
                        os.write(jsonBody.toString().toByteArray())
                    }

                    val responseCode = connection.responseCode
                    val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                        connection.inputStream.bufferedReader().readText()
                    } else {
                        connection.errorStream.bufferedReader().readText()
                    }

                    parseRecoveryResponse(response)

                } catch (e: Exception) {
                    RecoveryResponse(
                        success = false,
                        message = "Error de conexión: ${e.message}"
                    )
                }
            }
        }

        private fun parseLoginResponse(jsonString: String): LoginResponse {
            try {
                val json = JSONObject(jsonString)
                val success = json.getBoolean("success")
                val message = json.getString("message")

                val user = if (success && json.has("user")) {
                    val userJson = json.getJSONObject("user")
                    com.fraccionamiento.app.User(
                        id = userJson.getInt("id"),
                        username = userJson.getString("username"),
                        fullName = userJson.getString("full_name"),
                        userType = userJson.getString("user_type"),
                        fraccionamientoId = userJson.getInt("fraccionamiento_id"),
                        fraccionamientoName = userJson.getString("fraccionamiento_name")
                    )
                } else null

                return LoginResponse(success, message, user)

            } catch (e: Exception) {
                return LoginResponse(
                    success = false,
                    message = "Error procesando respuesta del servidor",
                    user = null
                )
            }
        }

        private fun parseRecoveryResponse(jsonString: String): RecoveryResponse {
            try {
                val json = JSONObject(jsonString)
                return RecoveryResponse(
                    success = json.getBoolean("success"),
                    message = json.getString("message")
                )
            } catch (e: Exception) {
                return RecoveryResponse(
                    success = false,
                    message = "Error procesando respuesta del servidor"
                )
            }
        }
    }
}