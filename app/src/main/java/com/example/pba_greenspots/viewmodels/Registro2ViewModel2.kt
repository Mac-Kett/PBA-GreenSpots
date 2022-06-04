package com.example.pba_greenspots.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pba_greenspots.models.Usuario
import com.example.pba_greenspots.models.UsuarioKT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registro2ViewModel2 : ViewModel() {
    // TODO: Implement the ViewModel

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth;

    fun crearUsuario(nombre : String, email : String, password : String, pais: String){
        var usuario : UsuarioKT = UsuarioKT(nombre, email,
            password, pais)
        //db.collection("Usuarios").document("uid").set(usuario)
        db.collection("Users").add(usuario)
    }

}