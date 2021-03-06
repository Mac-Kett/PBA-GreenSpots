package com.example.pba_greenspots.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.NavigationActivity;
import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment extends Fragment {

    private EditText nombreCompleto, email, contrasena;
    private Spinner spPaises;
    private Button btnRegister;
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        spPaises = v.findViewById(R.id.spPaises);
        btnRegister = v.findViewById(R.id.btnRegister);

        METODOS_COMPLEMENTARIOS.completarSpinnerPaises(spPaises, requireContext());

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
       // String country = pais.getText().toString().trim();
        String country = spPaises.getSelectedItem().toString().trim();
        String typeUser = "1";

        if (validarCampos(nombre, mail, contra, country)){
            mAuth.createUserWithEmailAndPassword(mail, contra)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user =  mAuth.getCurrentUser();
                                assert user != null;
                                idUser = user.getUid();
                                Usuario usuario = new Usuario(idUser,nombre,mail, contra, country, typeUser);
                                DocumentReference dr = db.collection("Users").document(idUser);
                                dr.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("REGISTRO","Se registro!");
                                        Toast.makeText(getActivity(),"Se ha registrado con exito!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                                        requireActivity().finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("REGISTRO","Error del registro!");
                                        Toast.makeText(getActivity(),"ERROR AL REGISTRARSE", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else{
                                Toast.makeText(getActivity(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else{
            Toast.makeText(getContext(), "Complete los datos!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validarCampos(String nombre, String mail, String contra, String country ) {
        return !(nombre.isEmpty() || mail.isEmpty() || contra.isEmpty() || contra.isEmpty());
    }
}