package com.example.pba_greenspots.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pba_greenspots.R
import com.example.pba_greenspots.entities.Reserve


class ReserveAdapter (var listaDB : MutableList<Reserve>,
                      val onItemClick : (Int) -> Unit ) // unit es el void de KT
    : RecyclerView.Adapter<ReserveAdapter.ReserveHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReserveAdapter.ReserveHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserve, parent, false)
        return (ReserveHolder(view))
    }

    override fun onBindViewHolder(holder: ReserveAdapter.ReserveHolder, position: Int) {
        holder.setName(listaDB[position].nombreUnidad)
        holder.setMunicipio(listaDB[position].municipio)
        holder.getCardView().setOnClickListener{
            onItemClick (position)
        }
    }

    override fun getItemCount(): Int {
        return listaDB.size
    }

    class ReserveHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View
        init {
            this.view = v
        }

        fun setName(name: String){
            val txt: TextView = view.findViewById(R.id.nameReserve)
            txt.text = name
        }

        fun setMunicipio(muni: String){
            val txt: TextView = view.findViewById(R.id.municipioReserve)
            txt.text = muni
        }

        fun getCardView () : CardView {
            return view.findViewById(R.id.itemCard)
        }
    }

}