package com.example.pba_greenspots.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterFragment extends Fragment {

    private EditText nombreCompleto, email, contrasena, pais;
    private Button btnRegister;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private String idUser;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //habilite al usuario que ya inició sesión
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        nombreCompleto = v.findViewById(R.id.nombre_registro);
        email = v.findViewById(R.id.email_registro);
        contrasena = v.findViewById(R.id.contrasenia_registro);
        pais = v.findViewById(R.id.pais_registro);
        btnRegister = v.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister(view);
            }
        });


        return v;
    }

    private void userRegister(View view) {

        String nombre = nombreCompleto.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String contra =contrasena.getText().toString().trim();
        String country = pais.getText().toString().trim();
        String typeUser = "3";

        mAuth.createUserWithEmailAndPassword(mail, contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mAuth = FirebaseAuth.getInstance();
                            user =  mAuth.getCurrentUser();
                            idUser = user.getUid();
                            Usuario usuario = new Usuario(idUser,nombre,mail, contra, country, typeUser);
                            //Log.d("usuario", {usuario})
                            db.collection("Users").add(usuario).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Se ha registrado con exito!", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getActivity(),"ERROR AL REGISTRARSE", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
//                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                Toast.makeText(getActivity(),"Se ha registrado con exito!", Toast.LENGTH_LONG).show();
//                                            } else {
//                                                Toast.makeText(getActivity(),"ERROR AL REGISTRARSE", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });

                        } else{
                            Toast.makeText(getActivity(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }




}