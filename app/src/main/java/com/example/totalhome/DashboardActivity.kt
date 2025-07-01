package com.example.totalhome

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvFraccionamientoName: TextView
    private lateinit var tvWelcomeUser: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var cardAvisos: LinearLayout
    private lateinit var cardPagos: LinearLayout
    private lateinit var cardQuejas: LinearLayout
    private lateinit var cardQrVisitas: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initViews()
        loadUserData()
        setupClickListeners()
    }

    private fun initViews() {
        tvFraccionamientoName = findViewById(R.id.tv_fraccionamiento_name)
        tvWelcomeUser = findViewById(R.id.tv_welcome_user)
        ivProfile = findViewById(R.id.iv_profile)
        cardAvisos = findViewById(R.id.card_avisos)
        cardPagos = findViewById(R.id.card_pagos)
        cardQuejas = findViewById(R.id.card_quejas)
        cardQrVisitas = findViewById(R.id.card_qr_visitas)
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val fullName = sharedPref.getString("full_name", "Usuario")
        val fraccionamientoName = sharedPref.getString("fraccionamiento_name", "Mi Fraccionamiento")

        tvFraccionamientoName.text = fraccionamientoName
        tvWelcomeUser.text = "Bienvenido, $fullName"
    }

    private fun setupClickListeners() {
        ivProfile.setOnClickListener {
            // Abrir menú de perfil o configuración
            Toast.makeText(this, "Perfil - Próximamente", Toast.LENGTH_SHORT).show()
        }

        cardAvisos.setOnClickListener {
            val intent = Intent(this, AvisosActivity::class.java)
            startActivity(intent)
        }

        cardPagos.setOnClickListener {
            val intent = Intent(this, PaymentsActivity::class.java)
            startActivity(intent)
        }

        cardQuejas.setOnClickListener {
            val intent = Intent(this, QuejasActivity::class.java)
            startActivity(intent)
        }

        cardQrVisitas.setOnClickListener {
            // Intent hacia QrVisitasActivity
            Toast.makeText(this, "QR Visitas - Próximamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        // Confirmar salida de la app
        super.onBackPressed()
        finishAffinity()
    }
}