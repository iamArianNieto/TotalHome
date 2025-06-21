package com.example.totalhome

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.totalhome.models.RecoveryResponse
import kotlinx.coroutines.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var etUsernameRecover: EditText
    private lateinit var btnSendData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        etUsernameRecover = findViewById(R.id.et_username_recover)
        btnSendData = findViewById(R.id.btn_send_data)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnSendData.setOnClickListener {
            val username = etUsernameRecover.text.toString().trim()

            if (username.isEmpty()) {
                etUsernameRecover.error = "Ingresa tu usuario"
                etUsernameRecover.requestFocus()
                return@setOnClickListener
            }

            sendRecoveryData(username)
        }
    }

    private fun sendRecoveryData(username: String) {
        btnSendData.isEnabled = false
        btnSendData.text = "Enviando..."

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    // Aquí iría la llamada real a tu API
                    // ApiService.recoverPassword(username)
                    simulateRecoveryCall(username)
                }

                if (response.success) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Se han enviado tus datos de acceso",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        response.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Error de conexión. Intenta nuevamente.",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnSendData.isEnabled = true
                btnSendData.text = "Enviar Datos"
            }
        }
    }

    private suspend fun simulateRecoveryCall(username: String): RecoveryResponse {
        delay(1500)

        return if (username.isNotEmpty()) {
            RecoveryResponse(
                success = true,
                message = "Datos enviados correctamente"
            )
        } else {
            RecoveryResponse(
                success = false,
                message = "Usuario no encontrado"
            )
        }
    }
}