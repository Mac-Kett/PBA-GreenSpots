package com.example.pba_greenspots;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Fragment_Reserves_ABM extends Fragment{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner spABM, spReserves;
    private static FragmentManager fragmentManager;
    private Button btnConfirmar, btnModificar, btnEliminar;


    public Fragment_Reserves_ABM() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reserves_abm, container, false);
        fragmentManager=getParentFragmentManager();

        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        spABM = view.findViewById(R.id.spABM);
        spReserves = view.findViewById(R.id.spReservas);

        //SOLO CUANDO EL USUARIO QUIERA DAR DE BAJA O MODIFICAR.
       //spReserves.setVisibility(View.GONE);

        // SPINNER ALTA, BAJAMODIFICACION.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.ABM, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spABM.setAdapter(adapter);

        spABM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        flujoAlta(spReserves);
                        break;
                    case 1:
                        flujoBajaModificacion(spReserves);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spABM.setBackgroundColor(Color.RED);
            }
        });


        //OBTENGO RESERVAS NATURALES Y LAS CARGO AL SPINNER CORRESPONDIENTE
        db.collection("Reserves")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<Map> lista = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                //AGREGAR AL SPINNER DE RESERVAS
                                lista.add(documentSnapshot.getData());
                            }
                            ArrayAdapter<Map> arrayAdapter = new ArrayAdapter<Map>(getContext(), android.R.layout.simple_spinner_item, lista);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spReserves.setAdapter(arrayAdapter);
                        }else{
                            Log.d(getTag(), "ERROR OBTENIENDO DATA!", task.getException());
                            Toast.makeText(getContext(), "ERROR! No pudimos completar la lista de reservas", Toast.LENGTH_LONG).show();
                        }

                    }
                });



        return view;
    }

    private void flujoAlta(Spinner spinner) {
        btnConfirmar.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);
    }

    private void flujoBajaModificacion(Spinner spinner) {
        btnEliminar.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.GONE);



    }

}