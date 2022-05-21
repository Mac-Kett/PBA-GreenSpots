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

public class MiPerfil extends Fragment {

    private MiPerfilViewModel mViewModel;
    private View v;
    EditText nombre;
    EditText apellido;
    EditText pais;
    EditText email;
    EditText contrasenia;
    Button btnVolver;
    public static MiPerfil newInstance() {
        return new MiPerfil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_registro, container, false);
        nombre = v.findViewById(R.id.nombre_perfil);
        apellido = v.findViewById(R.id.apellido_perfil);
        email = v.findViewById(R.id.email_perfil);
        contrasenia = v.findViewById(R.id.contrasenia_perfil);
        btnVolver = v.findViewById(R.id.btn_volver);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiPerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}