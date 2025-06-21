package com.example.totalhome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.totalhome.models.LoginResponse
import com.example.totalhome.models.User
import kotlinx.coroutines.*


class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvFraccionamientoName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupClickListeners()
        loadFraccionamientoConfig()
    }

    private fun initViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvFraccionamientoName = findViewById(R.id.fraccionamiento_name_login)
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateFields(username, password)) {
                performLogin(username, password)
            }
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun validateFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            etUsername.error = "Ingresa tu usuario"
            etUsername.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            etPassword.error = "Ingresa tu contraseña"
            etPassword.requestFocus()
            return false
        }

        return true
    }

    private fun performLogin(username: String, password: String) {
        btnLogin.isEnabled = false
        btnLogin.text = "Iniciando sesión..."

        // Simular llamada a API
        CoroutineScope(Dispatchers.Main).launch {
            try {
               /* val response = withContext(Dispatchers.IO) {
                    // Aquí iría la llamada real a tu API
                    // ApiService.login(username, password)
                    simulateApiCall(username, password)
                }*/

                /*if (response.success) {
                    saveUserSession(response.user)*/
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
               /* } else {
                    showError(response.message)
                }*/

            } catch (e: Exception) {
                showError("Error de conexión. Intenta nuevamente.")
            } finally {
                btnLogin.isEnabled = true
                btnLogin.text = "Iniciar Sesión"
            }
        }
    }

    private suspend fun simulateApiCall(username: String, password: String): LoginResponse {
        delay(1500) // Simular delay de red

        // Simulación - en producción esto vendría de tu API
        return if (username == "admin" && password == "123456") {
            LoginResponse(
                success = true,
                message = "Login exitoso",
                user = User(
                    id = 1,
                    username = username,
                    fullName = "Juan Pérez",
                    userType = "resident",
                    fraccionamientoId = 1,
                    fraccionamientoName = "Residencial Villa del Sol"
                )
            )
        } else {
            LoginResponse(
                success = false,
                message = "Usuario o contraseña incorrectos",
                user = null
            )
        }
    }

    private fun saveUserSession(user: User) {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
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

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun loadFraccionamientoConfig() {
        // Aquí cargarías la configuración específica del fraccionamiento
        // Por ahora usamos valores por defecto
        tvFraccionamientoName.text = "Residencial Villa del Sol"
    }
}
