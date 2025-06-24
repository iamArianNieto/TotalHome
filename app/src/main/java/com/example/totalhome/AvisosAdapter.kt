package com.example.totalhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AvisosAdapter(private val avisos: List<String>) :
    RecyclerView.Adapter<AvisosAdapter.AvisoViewHolder>() {

    // ViewHolder: cada elemento de la lista
    class AvisoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAviso: TextView = itemView.findViewById(R.id.tvAviso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aviso, parent, false)
        return AvisoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvisoViewHolder, position: Int) {
        holder.tvAviso.text = avisos[position]
    }

    override fun getItemCount(): Int = avisos.size
}
