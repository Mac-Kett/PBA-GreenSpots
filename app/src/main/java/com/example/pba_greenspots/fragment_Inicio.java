package com.example.pba_greenspots;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Inicio# newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Inicio extends Fragment {
    public View inicio;
    public Button btnLogin, btnRegister, btnLoginGoogle;

    public fragment_Inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inicio = inflater.inflate(R.layout.fragment__inicio,container,false);

        btnLogin = inicio.findViewById(R.id.btnLogin);
        btnRegister = inicio.findViewById(R.id.btnRegister);
        btnLoginGoogle = inicio.findViewById(R.id.btnLoginGoogle);

        return inicio;
    }


}