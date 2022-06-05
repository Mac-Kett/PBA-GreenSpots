package com.example.pba_greenspots;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class Fragment_Reserves_ABM extends Fragment{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner spABM,
            spReserves,
            spMunicipios,
            spTipoAdministracion,
            spCosto,
            spNivelesDificultad,
            spSenializacionServicios,
            spZonaServicios;
    private Button btnConfirmar,
            btnModificar,
            btnEliminar;
    private EditText etNombre,
            etInstrumentoPlanificacion,
            et_accesos,
            et_horarios,
            et_informacionAdicional,
            et_gestionesDesarrollo,
            et_telefono,
            et_correoPagWeb,
            et_infraestructura,
            et_actividadesDelArea,
            et_fauna,
            et_flora,
            et_clima,
            et_geologia,
            et_superficie,
            et_geolocalizacion,
            et_caracteristicasGenerales,
            et_fechaCreacion,
            et_importancia,
            et_instrumentoLegal,
            et_acceso;
    private LinearLayout formLinearLayout;
    private ScrollView scrollView;
    private ArrayList<Object> listaEditTexts;
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
        scrollView = view.findViewById(R.id.scrollView);
        formLinearLayout = view.findViewById(R.id.formLinearLayout);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        spABM = view.findViewById(R.id.spABM);
        spReserves = view.findViewById(R.id.spReservas);
        spMunicipios = view.findViewById(R.id.spMunicipios);
        spTipoAdministracion = view.findViewById(R.id.spTipoAdministracion);
        spCosto = view.findViewById(R.id.spCosto);
        spNivelesDificultad = view.findViewById(R.id.spNivelesDificultad);
        spSenializacionServicios = view.findViewById(R.id.spSenializacionServicios);
        spZonaServicios = view.findViewById(R.id.spZonaServicios);

        instanciarEditTextsFormulario(view);
        cargarArrayListEditTextsFormulario();


        METODOS_COMPLEMENTARIOS.completarSpinnerABM(spABM, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerMunicipios(spMunicipios, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerTipoAdministracion(spTipoAdministracion, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerCosto(spCosto, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerNivelesDificultad(spNivelesDificultad, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerSenializacionServicios(spSenializacionServicios, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerZonaServicios(spZonaServicios, requireContext());


        spReserves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarFormularioItemSeleccionado();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

       // OBTENGO RESERVAS NATURALES Y LAS CARGO AL SPINNER CORRESPONDIENTE
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

    //Éste Array es para el formulario (Alta, Baja, Mod)
    private void cargarArrayListEditTextsFormulario() {
        listaEditTexts = new ArrayList<>();
        listaEditTexts.add(etNombre);
        listaEditTexts.add(etInstrumentoPlanificacion);
        listaEditTexts.add(spMunicipios);
        listaEditTexts.add(spTipoAdministracion);
        listaEditTexts.add(spZonaServicios);
        listaEditTexts.add(spCosto);
        listaEditTexts.add(spNivelesDificultad);
        listaEditTexts.add(spSenializacionServicios);
        listaEditTexts.add(et_accesos);
        listaEditTexts.add(et_horarios);
        listaEditTexts.add(et_informacionAdicional);
        listaEditTexts.add(et_gestionesDesarrollo);
        listaEditTexts.add(et_telefono);
        listaEditTexts.add(et_correoPagWeb);
        listaEditTexts.add(et_infraestructura);
        listaEditTexts.add(et_actividadesDelArea);
        listaEditTexts.add(et_fauna);
        listaEditTexts.add(et_flora);
        listaEditTexts.add(et_clima);
        listaEditTexts.add(et_geologia);
        listaEditTexts.add(et_superficie);
        listaEditTexts.add(et_geolocalizacion);
        listaEditTexts.add(et_caracteristicasGenerales);
        listaEditTexts.add(et_fechaCreacion);
        listaEditTexts.add(et_importancia);
        listaEditTexts.add(et_instrumentoLegal);
        listaEditTexts.add(et_acceso);
    }

    private void actualizarSpinnerReservas() {
        //ACTUALIZAR AL SPINNER DE RESERVAS
        ArrayAdapter<ReservaNatural> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaReservasNaturales);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReserves.setAdapter(arrayAdapter);
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

    private void crearReserva() {
        //Valido campos
        if (!validarCampos()){
            Toast.makeText(getContext(),"Revise el formulario", Toast.LENGTH_LONG).show();
        }else{
            ReservaNatural reservaNatural = crearReservaNaturalConDatosFormulario();

            db.collection("Reserves")
                    .add(reservaNatural)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), R.string.msg_Alta_ReservaNatural_Exito, Toast.LENGTH_LONG).show();
                            Log.d(getTag(),"SE REGISTRO CON EXITO!");
                            reservaNatural.setId(documentReference.getId());
                            limpiarFormulario();
                            listaReservasNaturales.add(reservaNatural);
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
    private void modificarReserva() {
        ReservaNatural reservaNaturalFormulario = crearReservaNaturalConDatosFormulario();
        ReservaNatural reservaNaturalSeleccionada = (ReservaNatural) spReserves.getSelectedItem();

        reservaNaturalFormulario.setId(reservaNaturalSeleccionada.getId());

        //Me fijo si la reservaNatural seleccionada en el Spinner es igual a la de los datos del formulario (por eso tuve que agregarle el id de la seleccionada a la creada a partir del form)
        if (reservaNaturalFormulario != reservaNaturalSeleccionada){
            //valido campos
            if(validarCampos()){
                db.collection("Reserves")
                        .document(reservaNaturalFormulario.getId())
                        .set(reservaNaturalFormulario)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), R.string.msg_Modificacion_ReservaNatural_Exito, Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(getTag(), e.getMessage());
                                Toast.makeText(getContext(), R.string.msg_Modificacion_ReservaNatural_Error, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }else{
            Toast.makeText(getContext(), "No ha modificado ningun campo!", Toast.LENGTH_LONG).show();
        }
    }


    private ReservaNatural crearReservaNaturalConDatosFormulario() {
        //DENTRO DEL CONSTRUCTOR VAN LOS CAMPOS OBLIGATORIOS. LOS QUE NO LO SON, VAN FUERA USANDO SETTERS.
        ReservaNatural reservaCreada;
        reservaCreada = new ReservaNatural(
                etNombre.getText().toString().trim(),
                spMunicipios.getSelectedItem().toString().trim(),
                etInstrumentoPlanificacion.getText().toString().trim(),
                spTipoAdministracion.getSelectedItem().toString().trim(),
                spZonaServicios.getSelectedItem().toString().trim(),
                spCosto.getSelectedItem().toString().trim(),
                spNivelesDificultad.getSelectedItem().toString().trim(),
                spSenializacionServicios.getSelectedItem().toString().trim(),
                et_accesos.getText().toString().trim(),
                et_horarios.getText().toString().trim(),
                et_informacionAdicional.getText().toString().trim(),
                et_gestionesDesarrollo.getText().toString().trim(),
                et_telefono.getText().toString().trim(),
                et_correoPagWeb.getText().toString().trim(),
                et_infraestructura.getText().toString().trim(),
                et_actividadesDelArea.getText().toString().trim(),
                et_fauna.getText().toString().trim(),
                et_flora.getText().toString().trim(),
                et_clima.getText().toString().trim(),
                et_geologia.getText().toString().trim(),
                et_superficie.getText().toString().trim(),
                et_geolocalizacion.getText().toString().trim(),
                et_caracteristicasGenerales.getText().toString().trim(),
                et_fechaCreacion.getText().toString().trim(),
                et_importancia.getText().toString().trim(),
                et_instrumentoLegal.getText().toString().trim(),
                et_acceso.getText().toString().trim()
        );
        return reservaCreada;
    }

    private void instanciarEditTextsFormulario(View v) {
        etNombre = v.findViewById(R.id.et_nombreAnp);
        spMunicipios = v.findViewById(R.id.spMunicipios);
        etInstrumentoPlanificacion = v.findViewById(R.id.et_instrumentoPlanificacion);
        spTipoAdministracion = v.findViewById(R.id.spTipoAdministracion);
        spZonaServicios = v.findViewById(R.id.spZonaServicios);
        spCosto = v.findViewById(R.id.spCosto);
        spNivelesDificultad = v.findViewById(R.id.spNivelesDificultad);
        spSenializacionServicios = v.findViewById(R.id.spSenializacionServicios);
        et_accesos = v.findViewById(R.id.et_accesos);
        et_horarios = v.findViewById(R.id.et_horarios);
        et_informacionAdicional = v.findViewById(R.id.et_informacionAdicional);
        et_gestionesDesarrollo = v.findViewById(R.id.et_gestionesDesarrollo);
        et_telefono = v.findViewById(R.id.et_telefono);
        et_correoPagWeb = v.findViewById(R.id.et_correoPagWeb);
        et_infraestructura = v.findViewById(R.id.et_infraestructura);
        et_actividadesDelArea = v.findViewById(R.id.et_actividadesDelArea);
        et_fauna = v.findViewById(R.id.et_fauna);
        et_flora = v.findViewById(R.id.et_flora);
        et_clima = v.findViewById(R.id.et_clima);
        et_geologia = v.findViewById(R.id.et_geologia);
        et_superficie = v.findViewById(R.id.et_superficie);
        et_geolocalizacion = v.findViewById(R.id.et_geolocalizacion);
        et_caracteristicasGenerales = v.findViewById(R.id.et_caracteristicasGenerales);
        et_fechaCreacion = v.findViewById(R.id.et_fechaCreacion);
        et_importancia = v.findViewById(R.id.et_importancia);
        et_instrumentoLegal = v.findViewById(R.id.et_instrumentoLegal);
        et_acceso = v.findViewById(R.id.et_acceso);
    }

    private boolean validarCampos() {
        Boolean bool = true;
        for (Object editText : listaEditTexts) {
            if (editText instanceof EditText) {
                if (((EditText) editText).getText().toString().trim().isEmpty()) {
                    ((EditText) editText).setError(getString(R.string.msg_campoIncompleto));
                    ((EditText) editText).setHintTextColor(Color.RED);
                    bool = false;
                    break;
                }
            }
        }
            return bool;
    }

    private void limpiarFormulario() {
        /*for (EditText editText: listaEditTexts) {
            editText.setHintTextColor(getResources().getColor(R.color.default_hint));
            editText.setText("");
        }
        */
        for (Object o: listaEditTexts) {
            if(o instanceof EditText) {
                ((EditText) o).setHintTextColor(getResources().getColor(R.color.default_hint));
                ((EditText) o).setText("");
            } else {
                ((Spinner) o).setBackgroundColor(getResources().getColor(R.color.default_hint));
                ((Spinner) o).setSelection(0);
            }
        }


    }

    private void cargarFormularioItemSeleccionado() {
        ReservaNatural reservaNaturalSeleccionada;
        reservaNaturalSeleccionada = (ReservaNatural) spReserves.getSelectedItem();
        etNombre.setText(reservaNaturalSeleccionada.getNombreUnidad());
        etInstrumentoPlanificacion.setText(reservaNaturalSeleccionada.getInstrumentoPlanificacion());
        try {
            spMunicipios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getMunicipio(), getContext().getResources().getStringArray(R.array.municipios)));
        } catch (Exception e) {
            spMunicipios.setSelection(0);
        }
        try {
            spTipoAdministracion.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getAdministracionPublicaPrivada(), getContext().getResources().getStringArray(R.array.TIPO_ADMINISTRACION)));
        } catch (Exception e) {
            spTipoAdministracion.setSelection(0);
        }
        try {
            spZonaServicios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getZonaServicios(), getContext().getResources().getStringArray(R.array.TIPO_ADMINISTRACION)));
        } catch (Exception e) {
            spZonaServicios.setSelection(0);
        }
        try {
            spCosto.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getIngresoGratuitoPago(), getContext().getResources().getStringArray(R.array.COSTO)));
        } catch (Exception e) {
            spCosto.setSelection(0);
        }
        try {
            spNivelesDificultad.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getDificultadSenderismo(), getContext().getResources().getStringArray(R.array.COSTO)));
        } catch (Exception e) {
            spNivelesDificultad.setSelection(0);
        }
        try {
            spSenializacionServicios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getSenializacion(), getContext().getResources().getStringArray(R.array.COSTO)));
        } catch (Exception e) {
            spSenializacionServicios.setSelection(0);
        }
        et_accesos.setText(reservaNaturalSeleccionada.getAccesos());
        et_horarios.setText(reservaNaturalSeleccionada.getHorarios());
        et_informacionAdicional.setText(reservaNaturalSeleccionada.getInformacionAdicional());
        et_gestionesDesarrollo.setText(reservaNaturalSeleccionada.getGestionesDesarrollo());
        et_telefono.setText(reservaNaturalSeleccionada.getTelefono());
        et_correoPagWeb.setText(reservaNaturalSeleccionada.getCorreoPagWeb());
        et_infraestructura.setText(reservaNaturalSeleccionada.getInfraestructura());
        et_actividadesDelArea.setText(reservaNaturalSeleccionada.getActividadesDelArea());
        et_fauna.setText(reservaNaturalSeleccionada.getFauna());
        et_flora.setText(reservaNaturalSeleccionada.getFlora());
        et_clima.setText(reservaNaturalSeleccionada.getClima());
        et_geologia.setText(reservaNaturalSeleccionada.getGeologia());
        et_superficie.setText(reservaNaturalSeleccionada.getSuperficie());
        et_geolocalizacion.setText(reservaNaturalSeleccionada.getGeolocalizacion());
        et_caracteristicasGenerales.setText(reservaNaturalSeleccionada.getCaracteristicasGenerales());
        et_fechaCreacion.setText(reservaNaturalSeleccionada.getFechaCreacion());
        et_importancia.setText(reservaNaturalSeleccionada.getImportancia());
        et_instrumentoLegal.setText(reservaNaturalSeleccionada.getInstrumentoLegal());
        et_acceso.setText(reservaNaturalSeleccionada.getAcceso());

    }

    private int obtenerIndiceDelRecurso(String valorCampo, String[] lista){
        int i=0;
        int indiceBuscado=-1;

        try{
        while (i < lista.length && indiceBuscado == -1) {
            if (valorCampo.equalsIgnoreCase(lista[i])){
                indiceBuscado=i;
            }
            i++;
        }

        return indiceBuscado;
        } catch (Exception e){
        indiceBuscado=0;
        return indiceBuscado;
    }}

    private void flujoAlta() {
        scrollView.setVisibility(View.VISIBLE);
        formLinearLayout.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        spReserves.setVisibility(View.GONE);
        limpiarFormulario();
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