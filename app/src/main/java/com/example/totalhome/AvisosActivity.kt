package com.example.totalhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.totalhome.AvisosAdapter

class AvisosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avisos)

        val rvAvisos = findViewById<RecyclerView>(R.id.rv_avisos)
        val listaAvisos = listOf("Aviso 1", "Aviso 2", "Aviso 3")

        rvAvisos.layoutManager = LinearLayoutManager(this)
        rvAvisos.adapter = AvisosAdapter(listaAvisos)
    }
}
