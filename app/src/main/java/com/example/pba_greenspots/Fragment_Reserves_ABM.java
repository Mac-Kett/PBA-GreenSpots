package com.example.pba_greenspots;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Fragment_Reserves_ABM extends Fragment{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Spinner spABM, spReserves;
    private static FragmentManager fragmentManager;
    private static Button btnConfirmar, btnModificar, btnEliminar;
    private static EditText etNombre, etInstrumentoPlanificacion, etMunicipio;
    private static LinearLayout formLinearLayout;
    private static ScrollView scrollView;

    private static ArrayList<ReservaNatural> listaReservasNaturales;

    public Fragment_Reserves_ABM() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reserves_abm, container, false);
        fragmentManager=getParentFragmentManager();
        scrollView = view.findViewById(R.id.scrollView);
        formLinearLayout = view.findViewById(R.id.formLinearLayout);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        spABM = view.findViewById(R.id.spABM);
        spReserves = view.findViewById(R.id.spReservas);

        instanciarEditTextsFormulario(view);
        METODOS_COMPLEMENTARIOS.completarSpinnerABM(spABM, requireContext());

        spABM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        flujoAlta();
                        break;
                    case 1:
                        flujoBaja();
                        break;
                    case 2:
                        flujoModificacion();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spABM.setBackgroundColor(Color.RED);
            }
        });

        configuracionEventosListenersBotones();

        //METODOS_COMPLEMENTARIOS.obtenerReservasNaturales();

        //OBTENGO RESERVAS NATURALES Y LAS CARGO AL SPINNER CORRESPONDIENTE
        db.collection("Reserves")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            listaReservasNaturales=new ArrayList<ReservaNatural>();
                            ReservaNatural reservaNaturalActual;

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                reservaNaturalActual = documentSnapshot.toObject(ReservaNatural.class);
                                reservaNaturalActual.setId(documentSnapshot.getId());
                                listaReservasNaturales.add(reservaNaturalActual);
                                Log.d(getTag(), String.valueOf(listaReservasNaturales));
                            }

                            actualizarSpinnerReservas();

                        }else{
                            Log.d(getTag(), "ERROR OBTENIENDO DATA!", task.getException());
                            Toast.makeText(getContext(), "ERROR! No pudimos completar la lista de reservas", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return view;
    }

    private void actualizarSpinnerReservas() {
        //AGREGAR AL SPINNER DE RESERVAS
        ArrayAdapter<ReservaNatural> arrayAdapter = new ArrayAdapter<ReservaNatural>(getContext(), android.R.layout.simple_spinner_item, listaReservasNaturales);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReserves.setAdapter(arrayAdapter);
    }

    private void cargarFormulario() {
    }

    private void configuracionEventosListenersBotones() {
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearReserva();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarReserva();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarReserva();
            }
        });
    }

    private void modificarReserva() {
    }

    private void eliminarReserva() {
        ReservaNatural reservaEliminar;
        reservaEliminar= (ReservaNatural) spReserves.getSelectedItem();

        db.collection("Reserves")
                .document(reservaEliminar.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), R.string.msg_Baja_ReservaNatural_Exito, Toast.LENGTH_LONG).show();
                        listaReservasNaturales.remove(reservaEliminar);
                        actualizarSpinnerReservas();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(getTag(), "onFailure: "+ e.getMessage());
                        Toast.makeText(getContext(), R.string.msg_Baja_ReservaNatural_Error, Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void instanciarEditTextsFormulario(View v) {
        etNombre = v.findViewById(R.id.et_nombreAnp);
        etMunicipio = v.findViewById(R.id.et_municipio);
        etInstrumentoPlanificacion = v.findViewById(R.id.et_instrumentoPlanificacion);
    }

    private void crearReserva() {
        String nombre, instrumentoPlanificacion, municipio;

        nombre= etNombre.getText().toString().trim();
        instrumentoPlanificacion= etInstrumentoPlanificacion.getText().toString().trim();
        municipio= etMunicipio.getText().toString().trim();

        //Valido campos
        if (    !validarCampo(etNombre, nombre) ||
                !validarCampo(etMunicipio, municipio) ||
                !validarCampo(etInstrumentoPlanificacion, instrumentoPlanificacion)){
            Toast.makeText(getContext(),"Revise el formulario", Toast.LENGTH_LONG).show();
        }else{
            ReservaNatural reservaNatural;
            reservaNatural = new ReservaNatural(nombre,instrumentoPlanificacion,municipio);

            db.collection("Reserves")
                    .add(reservaNatural)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), R.string.msg_Alta_ReservaNatural_Exito, Toast.LENGTH_LONG).show();
                            Log.d(getTag(),"SE REGISTRO CON EXITO!");
                            limpiarFormulario();
                            actualizarSpinnerReservas();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), R.string.msg_Alta_ReservaNatural_Error, Toast.LENGTH_LONG).show();
                            Log.d(getTag(),"OCURRIO UN ERROR", e);
                        }
                    });
        }
    }

    private void limpiarFormulario() {
        etNombre.setText("");
        etMunicipio.setText("");
        etInstrumentoPlanificacion.setText("");
    }

    private boolean validarCampo(EditText et, String str) {
        boolean bool=true;
        if (str.isEmpty()){
            et.setError(getString(R.string.msg_campoIncompleto));
            et.setHintTextColor(Color.RED);
            bool = false;
        }
        return bool;
    }

    private void flujoAlta() {
        scrollView.setVisibility(View.VISIBLE);
        formLinearLayout.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        spReserves.setVisibility(View.GONE);
    }
    private void flujoModificacion() {
        scrollView.setVisibility(View.VISIBLE);
        formLinearLayout.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.VISIBLE);
        spReserves.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);

    }
    private void flujoBaja() {
        spReserves.setVisibility(View.VISIBLE);
        btnEliminar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        formLinearLayout.setVisibility(View.GONE);
        btnModificar.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);
    }

}