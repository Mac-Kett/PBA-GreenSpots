package com.example.pba_greenspots.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.ABM_Reserve.FilaImagen;
import com.example.pba_greenspots.entities.Reserve;
import com.example.pba_greenspots.entities.Reserve;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import io.grpc.Context;

public class Fragment_Reserves_ABM extends Fragment{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();


    private Spinner spABM,
            spReserves,
            spMunicipios,
            spTipoAdministracion,
            spCosto,
            spNivelesDificultad,
            spSenializacionServicios,
            spZonaServicios;
    private ActivityResultLauncher<Intent> activityResultLauncherCSV;
    private ActivityResultLauncher<Intent> activityResultLauncherImagenes;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private Button btnConfirmar,
            btnModificar,
            btnEliminar,
            btnImportarCSV,
            btnImagenes;
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
            et_acceso,
            et_personal;
    private LinearLayout formLinearLayout, imagenesLinearLayoutContainer;
    private ScrollView scrollView;
    private ArrayList<Object> listaEditTexts;
    private ArrayList<Uri> listaUrisImagenes;
    private ArrayList<String> listaURLs;
    private ArrayList<Reserve> listaReservasNaturales;
    private StorageReference storageReference;
    private int cantImagenes;

    public Fragment_Reserves_ABM() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference=storage.getReference();
        //la lista que contendra las URIs de las imagenes que se carguen desde el movil (para el ALTA).
        listaUrisImagenes = new ArrayList<>();
        listaURLs = new ArrayList<>();
        activityResultLauncherCSV = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK){
                    ArrayList<Reserve> listaReservas = obtenerReservasNaturalesCSV(result.getData().getData());
                    for (Reserve Reserve:listaReservas) {
                        putReservaNatural(Reserve);
                    }
                }else{
                    Toast.makeText(getContext(), "Ocurrio un error.", Toast.LENGTH_LONG).show();
                }
            }
        });

        activityResultLauncherImagenes = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    assert result.getData() != null;
                    listaUrisImagenes.addAll(obtenerListaUrisImagenes(result.getData()));
                    generarImageViews();

                }else{
                    //NO SELECCIONO NADA O VOLVIO PARA ATRAS.
                    Log.d(getTag(), "RESULT CODE != OK ++ ERROR?", null);
                }
            }
        });

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    Toast.makeText(requireContext(), "Permiso otorgado", Toast.LENGTH_SHORT).show();
                    seleccionadorDeArchivosCSV();
                }else{
                    Toast.makeText(requireContext(), "Permitanos acceder para poder realizar la importacion.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void generarImageViews() {
        //hacer lista de imageviews como atributo de la clase
        imagenesLinearLayoutContainer.removeAllViews();
        for (int i=0; i<listaUrisImagenes.size(); i++) {
            Uri uriActual = listaUrisImagenes.get(i);

            LinearLayout lyActual = new LinearLayout(getContext());
            lyActual.setOrientation(LinearLayout.HORIZONTAL);
            lyActual.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageURI(uriActual);

            Button btnQuitarActual = new Button(getContext());
            btnQuitarActual.setText("-");
            btnQuitarActual.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            btnQuitarActual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagenesLinearLayoutContainer.removeView(lyActual);
                    listaUrisImagenes.remove(uriActual);
                }
            });
            lyActual.addView(imageView);
            lyActual.addView(btnQuitarActual);
            lyActual.setVisibility(View.VISIBLE);
            imagenesLinearLayoutContainer.addView(lyActual);
        }
        imagenesLinearLayoutContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reserves_abm, container, false);
        scrollView = view.findViewById(R.id.scrollView);
        formLinearLayout = view.findViewById(R.id.formLinearLayout);
        imagenesLinearLayoutContainer = view.findViewById(R.id.imagenesLayoutContainer);

        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnImportarCSV = view.findViewById(R.id.btnImportarCSV);
        btnImagenes = view.findViewById(R.id.btnImagenes);

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
                //Al seleccionar un ITEM en la lista, se carga el formulario con sus datos.
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
                            listaReservasNaturales=new ArrayList<Reserve>();
                            Reserve reservaNaturalActual;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                reservaNaturalActual = documentSnapshot.toObject(Reserve.class);
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
        listaEditTexts.add(et_personal);
    }

    private void actualizarSpinnerReservas() {
        //ACTUALIZAR AL SPINNER DE RESERVAS
        ArrayAdapter<Reserve> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaReservasNaturales);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReserves.setAdapter(arrayAdapter);
    }
    private ArrayList<Uri> obtenerListaUrisImagenes(Intent data) {
        //O SELECCIONO UNA, O SELECCIONO VARIAS.
        ArrayList<Uri> listaUris = new ArrayList<>();
        if (data.getData()!=null){
            listaUris.add(data.getData());
        } else{
            ClipData clipData = data.getClipData();
            for (int i=0; i < clipData.getItemCount(); i++){
                listaUris.add(clipData.getItemAt(i).getUri());
            }
        }
        return listaUris;
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

        btnImportarCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(true);
            }
        });

        btnImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {requestPermission(false);}
        });
    }

    private ArrayList<Reserve> obtenerReservasNaturalesCSV(Uri uri){
        BufferedReader bufferedReader = null;
        String line;
        ArrayList<Reserve> listaReservasNaturales = new ArrayList<>();
        Reserve reservaActual;
        try
        {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int i=0;
            String[] splitted;
            while ((line = bufferedReader.readLine()) != null) {
                splitted = line.split(";");

                //Es una manera de sacar a la primera fila (son los titulos de los campos).
                if (i>0){
                    try {
                        reservaActual = new Reserve();

                        //SI ALGUNO DE LOS CAMPOS OBLIGATORIOS ES NULL NO SE DEBE SUMAR EL OBJETO A LA LISTA. ES POR ESO QUE SE LANZA UN ERROR.
                        if(splitted[0].isEmpty() || splitted[27].isEmpty()){
                            throw new Exception();
                        }else {
                            //SET DEL ATRIBUTO, CAMPO POR CAMPO.
                            reservaActual.setNombreUnidad(splitted[0].trim());
                            reservaActual.setInstrumentoPlanificacion(splitted[1].trim());
                            reservaActual.setPersonal(splitted[2].trim());
                            reservaActual.setInstrumentoLegal(splitted[3].trim());
                            reservaActual.setAcceso(splitted[4].trim());
                            reservaActual.setImportancia(splitted[5].trim());
                            reservaActual.setFechaCreacion(splitted[6].trim());
                            reservaActual.setCaracteristicasGenerales(splitted[7].trim());
                            reservaActual.setGeolocalizacion(splitted[8].trim());
                            reservaActual.setSuperficie(splitted[9].trim());
                            reservaActual.setGeologia(splitted[10].trim());
                            reservaActual.setClima(splitted[11].trim());
                            reservaActual.setFlora(splitted[12].trim());
                            reservaActual.setFauna(splitted[13].trim());
                            reservaActual.setActividadesDelArea(splitted[14].trim());
                            reservaActual.setInfraestructura(splitted[15].trim());
                            reservaActual.setCorreoPagWeb(splitted[16].trim());
                            reservaActual.setTelefono(splitted[17].trim());
                            reservaActual.setGestionesDesarrollo(splitted[18].trim());
                            reservaActual.setInformacionAdicional(splitted[19].trim());
                            reservaActual.setHorarios(splitted[20].trim());
                            reservaActual.setAccesos(splitted[21].trim());
                            reservaActual.setPersonal(splitted[22].trim());

                            //VALIDAR QUE LOS STRINGS DE ESTOS CAMPOS COINCIDAN CON ALGUN STRING DENTRO DEL ARRAYSTRING DEL RECURSO EN VALUES->STRINGS.XML
                            //TODO Reveer que los valores del Split en determinada posición coincidan con las listas que yo puse. ADRI
                            if (reservaActual.validarAtributo(splitted[22].trim(), requireContext().getResources().getStringArray(R.array.SENIALIZACION_DE_SENDEROS))) {
                                reservaActual.setSenializacion(splitted[22].trim());
                            }
                            if (reservaActual.validarAtributo(splitted[23].trim(), requireContext().getResources().getStringArray(R.array.NIVELES_DIFICULTAD))) {
                                reservaActual.setDificultadSenderismo(splitted[23].trim());
                            }
                            if (reservaActual.validarAtributo(splitted[24].trim(), requireContext().getResources().getStringArray(R.array.COSTO))) {
                                reservaActual.setIngresoGratuitoPago(splitted[24].trim());
                            }
                            if (reservaActual.validarAtributo(splitted[25].trim(), requireContext().getResources().getStringArray(R.array.ZONA_DE_SERVICIOS))) {
                                reservaActual.setZonaServicios(splitted[25].trim());
                            }
                            if (reservaActual.validarAtributo(splitted[26].trim(), requireContext().getResources().getStringArray(R.array.TIPO_ADMINISTRACION))) {
                                reservaActual.setAdministracionPublicaPrivada(splitted[26].trim());
                            }
                            if (reservaActual.validarAtributo(splitted[27].trim(), requireContext().getResources().getStringArray(R.array.MUNICIPIOS))) {
                                reservaActual.setMunicipio(splitted[27].trim());
                            }else {
                                throw new Exception();
                            }
                            listaReservasNaturales.add(reservaActual);
                        }
                    }catch (Exception e){
                        e.getMessage();
                    }
                }
                i++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaReservasNaturales;
    }
    private void requestPermission(boolean bool){
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d(getTag(), "Permiso ya otorgado previamente!", null);
            if(bool){
                seleccionadorDeArchivosCSV();
            }else{
                seleccionadorDeArchivosImagenes();
            }

        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void seleccionadorDeArchivosImagenes() {
        Intent intent = new Intent()
                .setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE)
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activityResultLauncherImagenes.launch(intent);
    }
    private void seleccionadorDeArchivosCSV() {

        Intent intent = new Intent()
                .setAction(Intent.ACTION_OPEN_DOCUMENT)
                .setType("text/comma-separated-values")
                .addCategory(Intent.CATEGORY_OPENABLE);

        activityResultLauncherCSV.launch(intent);
    }
    private void crearReserva() {
        //Valido campos
        if (!validarCampos()){
            Toast.makeText(getContext(),"Revise el formulario", Toast.LENGTH_LONG).show();
        }else{
            Reserve reservaNatural = crearReservaNaturalConDatosFormulario();
            impactarStorage_BaseDatos(reservaNatural);

        }
    }

    private void impactarStorage_BaseDatos(Reserve reservaNatural){
        for (Uri uri: listaUrisImagenes){
            String[] nombre= uri.getLastPathSegment().split("/");
            StorageReference refImagen = storageReference.child("images/"+reservaNatural.getId()+"/"+nombre[nombre.length-1]);

            refImagen.putFile(uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            cantImagenes++;
                            if (task.isSuccessful()){
                                String urlString = refImagen.getDownloadUrl().toString();
                                Log.d(getTag(),urlString, null);
                                listaURLs.add(urlString);

                                //Si el contador es igual a la cantidad de imagenes que se van a subir, se ejecuta lo siguiente.
                                if (cantImagenes==listaUrisImagenes.size()){
                                    reservaNatural.setListaImagenes(listaURLs);
                                    putReservaNatural(reservaNatural);
                                    cantImagenes=0;
                                }
                            }else{
                                Log.d(getTag(), "Failed : "+task.getResult().getError() ,null);
                            }
                        }
                    });
        }
    }

    private void putReservaNatural(Reserve reservaNatural) {
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
                        Log.d(getTag(),"OCURRIO UN ERROR: "+e.getMessage(), e);
                    }
                });
    }


    private void eliminarReserva() {
        Reserve reservaEliminar;
        reservaEliminar= (Reserve) spReserves.getSelectedItem();

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
        Reserve reservaNaturalFormulario = crearReservaNaturalConDatosFormulario();
        Reserve reservaNaturalSeleccionada = (Reserve) spReserves.getSelectedItem();

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


    private Reserve crearReservaNaturalConDatosFormulario() {
        //DENTRO DEL CONSTRUCTOR VAN LOS CAMPOS OBLIGATORIOS. LOS QUE NO LO SON, VAN FUERA USANDO SETTERS.
        Reserve reservaCreada;
        reservaCreada = new Reserve(
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
                et_acceso.getText().toString().trim(),
                et_personal.getText().toString().trim()
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
        et_personal = v.findViewById(R.id.et_personal);
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

        for (Object o: listaEditTexts) {
            if(o instanceof EditText) {
                ((EditText) o).setHintTextColor(getResources().getColor(R.color.default_hint));
                ((EditText) o).setText("");
            } else {
                ((Spinner) o).setBackgroundColor(getResources().getColor(R.color.default_hint));
                ((Spinner) o).setSelection(0);
            }
        }
        imagenesLinearLayoutContainer.removeAllViews();
        listaUrisImagenes.clear();
        listaURLs.clear();

    }

    private void cargarFormularioItemSeleccionado() {
        Reserve reservaNaturalSeleccionada;
        reservaNaturalSeleccionada = (Reserve) spReserves.getSelectedItem();
        etNombre.setText(reservaNaturalSeleccionada.getNombreUnidad());
        etInstrumentoPlanificacion.setText(reservaNaturalSeleccionada.getInstrumentoPlanificacion());
        try {
            spMunicipios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getMunicipio(), getContext().getResources().getStringArray(R.array.MUNICIPIOS)));
        } catch (Exception e) {
            spMunicipios.setSelection(0);
        }
        try {
            spTipoAdministracion.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getAdministracionPublicaPrivada(), getContext().getResources().getStringArray(R.array.TIPO_ADMINISTRACION)));
        } catch (Exception e) {
            spTipoAdministracion.setSelection(0);
        }
        try {
            spZonaServicios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getZonaServicios(), getContext().getResources().getStringArray(R.array.ZONA_DE_SERVICIOS)));
        } catch (Exception e) {
            spZonaServicios.setSelection(0);
        }
        try {
            spCosto.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getIngresoGratuitoPago(), getContext().getResources().getStringArray(R.array.COSTO)));
        } catch (Exception e) {
            spCosto.setSelection(0);
        }
        try {
            spNivelesDificultad.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getDificultadSenderismo(), getContext().getResources().getStringArray(R.array.NIVELES_DIFICULTAD)));
        } catch (Exception e) {
            spNivelesDificultad.setSelection(0);
        }
        try {
            spSenializacionServicios.setSelection(obtenerIndiceDelRecurso(reservaNaturalSeleccionada.getSenializacion(), getContext().getResources().getStringArray(R.array.SENIALIZACION_DE_SENDEROS)));
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
        btnImportarCSV.setVisibility(View.VISIBLE);
        imagenesLinearLayoutContainer.setVisibility(View.VISIBLE);
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
        imagenesLinearLayoutContainer.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        btnImportarCSV.setVisibility(View.GONE);

    }
    private void flujoBaja() {
        spReserves.setVisibility(View.VISIBLE);
        btnEliminar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        formLinearLayout.setVisibility(View.GONE);
        btnModificar.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);
        btnImportarCSV.setVisibility(View.GONE);
        imagenesLinearLayoutContainer.setVisibility(View.GONE);
    }

}