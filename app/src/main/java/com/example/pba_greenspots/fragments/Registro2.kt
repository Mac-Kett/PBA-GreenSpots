package com.example.pba_greenspots.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pba_greenspots.R
import com.example.pba_greenspots.viewmodels.Registro2ViewModel2
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registro2 : Fragment() {

    companion object {
        fun newInstance() = Registro2()
    }

    private lateinit var viewModel: Registro2ViewModel2
    lateinit var v: View
    lateinit var nombre: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var pais: EditText
    lateinit var btnRegistro : Button
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth;
    private lateinit var registerFrameLayout: ConstraintLayout
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_registro3, container, false)
        nombre = v.findViewById(R.id.nombre)     // ver layout
        email = v.findViewById(R.id.email)   //ver layout
        password = v.findViewById(R.id.password) // ver layout
        pais = v.findViewById(R.id.pais) // ver layout
        btnRegistro = v.findViewById(R.id.boton_registro_cuenta) // ver layout
        registerFrameLayout = v.findViewById(R.id.frameLayout_registro)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database.reference.child("User")
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Registro2ViewModel2::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        /*var usuario : Cliente = Cliente("Pedro")*/

        /*db.collection("mascotas").document(mascota.nombre).set(mascota)

        db.collection("mascotas").add(usuario)

        bttnCrearCuenta.setOnClickListener() {
            if(email.text.isNotEmpty() && contrasenia.text.isNotEmpty()){
                viewModel.crearCuenta(email.text.toString(), contrasenia.text.toString())
            }
        }*/
        var onClickListener = btnRegistro.setOnClickListener() {
            if (nombre.text.isNotEmpty() && email.text.isNotEmpty()
                && password.text.isNotEmpty() && pais.text.isNotEmpty()
            ) {
                viewModel.crearUsuario(
                    nombre.text.toString(), email.text.toString(), password.text.toString(), pais.text.toString()
                )
                Snackbar.make(
                    registerFrameLayout,
                    "Creación exitosa",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
                var email: String = email.text.toString()
                var password: String = password.text.toString()
                var nombre: String = nombre.text.toString()
                var pais: String = pais.text.toString()

                dbReference = database.reference.child("Users")
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isComplete) {
                            val user: FirebaseUser? = auth.currentUser
                            val userBD = dbReference.child(user?.uid!!)

                            userBD.child("email").setValue(email)
                            userBD.child("password").setValue(password)
                        }
                    }

            } else {
                Snackbar.make(
                    registerFrameLayout,
                    "Error: Campos faltantes o erroneos",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }

        }
    }
}