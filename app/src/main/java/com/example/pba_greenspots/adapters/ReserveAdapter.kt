package com.example.pba_greenspots.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pba_greenspots.R
import com.example.pba_greenspots.entities.Reserve
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

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
        holder.setHorario(listaDB[position].horarios)
        holder.setImage(listaDB[position].id)
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

        fun setImage(id:String){
            val storageReference = FirebaseStorage.getInstance().reference;
            val refReserve = storageReference.child("images").child(id)
                    refReserve
                        .list(1)
                        .addOnCompleteListener(OnCompleteListener { it ->
                            val imageView: ImageView = view.findViewById(R.id.imgReserve)
                            if (it.isSuccessful){
                                var references = it.result.items
                                if (references.size>0){
                                    references[0]
                                            .downloadUrl
                                            .addOnCompleteListener {
                                                if (it.isSuccessful){
                                                    imageView.visibility = View.VISIBLE
                                                    val url = it.result
                                                    Picasso.get()
                                                            .load(url)
                                                            .fit()
                                                            .into(imageView)
                                                }else{
                                                    Log.d(null, it.exception?.message+" reserva: "+id, null)
                                                }
                                            }
                                }else{
                                    imageView.visibility = View.GONE
                                }
                            }
                        })
        }

        fun setMunicipio(muni: String){
            val txt: TextView = view.findViewById(R.id.municipioReserve)
            txt.text = muni
        }
        fun setHorario(horario: String){
            val txt: TextView = view.findViewById(R.id.horarioReserve)
            txt.text = horario
        }

        fun getCardView () : CardView {
            return view.findViewById(R.id.itemCard)
        }
    }

}