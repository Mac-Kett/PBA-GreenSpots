package com.example.pba_greenspots;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class registro extends Fragment {

    private RegistroViewModel mViewModel;
    private View v;
    EditText nombre;
    EditText apellido;
    Spinner pais;
    EditText email;
    EditText contrasenia;
    EditText confirmarContrasenia;
    Button btnRegistrarse;

    public static registro newInstance() {
        return new registro();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_registro, container, false);
        nombre = v.findViewById(R.id.nombre_registro);
        apellido = v.findViewById(R.id.apellido_registro);
        email = v.findViewById(R.id.email_registro);
        contrasenia = v.findViewById(R.id.contrasenia1_registro);
        confirmarContrasenia = v.findViewById(R.id.contrasenia2_registro);
        btnRegistrarse = v.findViewById(R.id.btn_registrarse);
        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistroViewModel.class);
        // TODO: Use the ViewModel
    }



}