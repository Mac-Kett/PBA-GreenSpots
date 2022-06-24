package com.example.pba_greenspots.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pba_greenspots.MainActivity;
import com.example.pba_greenspots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LogOutFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String idUser;

    private Button logoutCancelar;
    private Button logoutAceptar;

    public LogOutFragment() {
        // Required empty public constructor
    }


    public static LogOutFragment newInstance(String param1, String param2) {
        LogOutFragment fragment = new LogOutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_log_out, container, false);

        logoutAceptar = v.findViewById(R.id.logoutAceptar);
        logoutCancelar= v.findViewById(R.id.logoutCancelar);

        //logoutAceptar.setOnClickListener(); enviar el logout
        logoutAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                signOutUser();
            }
        });


        return v;
    }

   /* public void  onClickLogOut(){ //pensar como hacer el logout
        firebaseAuth.signOut();
        signOutUser();
    }*/


    //finaliza la activity y nos devuelve al main activity con su inicio de sesion

    private void signOutUser() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}