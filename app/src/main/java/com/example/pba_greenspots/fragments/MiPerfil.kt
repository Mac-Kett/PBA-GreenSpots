package com.example.pba_greenspots.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.pba_greenspots.R
import com.example.pba_greenspots.models.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MiPerfil : Fragment() {

    companion object {
        fun newInstance() = MiPerfil()
    }

    private lateinit var viewModel: MiPerfilViewModel
    lateinit var v : View
    lateinit var nombre : EditText

    //acceso a la base de datos
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         v = inflater.inflate(R.layout.fragment_mi_perfil, container, false)
        nombre = v.findViewById(R.id.campoNombre)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MiPerfilViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
    /*
        var mascota : Mascota = Mascota("Pedro",Mascota.Constants.typePerro,"Colie",2,"imagen.com")

        db.collection("mascotas").document(mascota.nombre).set(mascota)
        db.collection("mascotas").add(mascota)

       viewModel.initTestList()

        for (mascota in viewModel.mascotas) {
            db.collection("mascotas").document(mascota.nombre).set(mascota)
        }
  */


// Leer datos una sola vez
        var docRef = db.collection("users").document("prueba")

        docRef.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    val usuario  = dataSnapshot.toObject<Usuario>()
                    Log.d("Test", "DocumentSnapshot data: ${usuario.toString()}")
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }




    }


}