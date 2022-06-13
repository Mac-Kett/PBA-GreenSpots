package com.example.pba_greenspots.fragments

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pba_greenspots.DetailsReserveFragment
import com.example.pba_greenspots.R
import com.example.pba_greenspots.adapters.ReserveAdapter
import com.example.pba_greenspots.entities.Reserve
import com.example.pba_greenspots.repository.ReserveRepository
import com.example.pba_greenspots.viewmodels.ReservesViewModel
import com.google.android.material.snackbar.Snackbar

//import com.google.firebase.firestore.ktx.toObject

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import android.os.Bundle as Bundle

class ReservesFragment : Fragment() {

    companion object {
        fun newInstance() = ReservesFragment()
    }

    private lateinit var viewModel: ReservesViewModel
    lateinit var v: View
    lateinit var recycler : RecyclerView
    lateinit var adapter : ReserveAdapter
    private val reserveRepository = ReserveRepository ()
    var db = Firebase.firestore
    private var listaDB : MutableList<Reserve> = mutableListOf()
    private lateinit var reserva : Reserve


    //var reserveList : MutableList<Reserve> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_reserves2, container, false)
        recycler = v.findViewById(R.id.recReserve)

        return v
    }

//    private fun obtenerReservasNaturales() {
//
//    db.collection("Reverves")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    listaDB.add(document.toObject())
//                }
//                Log.d("doc", listaDB.toString())
//            }
//            .addOnFailureListener { exception ->
//                Log.d("doc", "Error getting documents.", exception)
//            }
//    }

    override fun onStart() {
        super.onStart()

        //obtenerReservasNaturales();
        db.collection("Reserves")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d("test", document.toObject<Reserve>().toString())
                   listaDB.add(document.toObject())
                }
                recycler.adapter = ReserveAdapter(listaDB){pos->
                    onItemClick(pos)
                }
                Log.d("test", "123")
            }
            .addOnFailureListener { exception ->
                Log.d("test", "Error getting documents.", exception)
            }

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
       // recycler.adapter = ReserveAdapter(reserveRepository.getReserves()){pos->


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReservesViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun onItemClick (position : Int){
        reserva = listaDB[position]
        lateinit var bundle : Bundle

        bundle = Bundle()
        var detalle : DetailsReserveFragment = DetailsReserveFragment()

        bundle.putParcelable("Reserve", reserva)

        //detalle.arguments(bundle)


        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detalle)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()


        //deberia llevar al fragment detalle de la reseva
    Snackbar.make(v, "ReservaOK", Snackbar.LENGTH_SHORT).show()
    //Snackbar.make(v, reserveRepository.getReserves()[position].municipio, Snackbar.LENGTH_SHORT).show()
    }

}