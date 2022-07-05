package com.example.pba_greenspots.fragments;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
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

import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.Gestor;
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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class Fragment_Gestores_ABM extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser userDB;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    private Spinner spMunicipios2,
            spABM2,
            spGestores;
    private EditText et_Nombre,
            et_email,
            et_pais,
            et_contrasenia,
            et_typeUser;
    private Button btnConfirmar,
            btnModificar,
            btnEliminar;
    private LinearLayout formLinearLayout;
    private ScrollView scrollView;
    private ArrayList<Object> listaEditTexts;
    private static ArrayList<Gestor> listaGestores;
    private Gestor user;

    public Fragment_Gestores_ABM() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_reserves_abm);

        //spMunicipios2.setSelection(Arrays.asList(getResources().getStringArray(R.array.MUNICIPIOS)).indexOf(spMunicipios2));
        //spMunicipios2.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__gestores__a_b_m, container, false);
        scrollView = view.findViewById(R.id.scrollView);
        formLinearLayout = view.findViewById(R.id.formLinearLayout2);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        spABM2 = view.findViewById(R.id.spABM2);
        spGestores = view.findViewById(R.id.spGestores);
        spMunicipios2 = view.findViewById(R.id.spMunicipios2);

        user= obtenerUser();

        instanciarEditTextsFormulario(view);
        cargarArrayListEditTextsFormulario();

        METODOS_COMPLEMENTARIOS.completarSpinnerABM(spABM2, requireContext());
        METODOS_COMPLEMENTARIOS.completarSpinnerMunicipios(spMunicipios2, requireContext());

        spGestores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spGestores.getSelectedItem().equals(getResources().getStringArray(R.array.ABM)[2])){
                    cargarFormularioDeGestor((Gestor)spGestores.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spGestores.setBackgroundColor(Color.RED);
            }
        }
        );

        spABM2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                spABM2.setBackgroundColor(Color.RED);
            }
        });

        configuracionEventosListenersBotones();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaGestores = new ArrayList<Gestor>();
                            Gestor gestorActual;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                gestorActual = documentSnapshot.toObject(Gestor.class);
                                gestorActual.setId(documentSnapshot.getId());
                                listaGestores.add(gestorActual);
                                Log.d(getTag(), String.valueOf(listaGestores));
                            }
                            actualizarSpinnerGestores();
                        } else {
                            Log.d(getTag(), "ERROR OBTENIENDO DATA!", task.getException());
                            Toast.makeText(getContext(), "ERROR! No pudimos completar la lista", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        getAllGestores();
        return view;
    }

    //Éste Array es para el formulario (Alta, Baja, Mod)
    private void cargarArrayListEditTextsFormulario() {
        listaEditTexts = new ArrayList<>();
        listaEditTexts.add(et_Nombre);
        listaEditTexts.add(et_contrasenia);
        listaEditTexts.add(et_email);
        listaEditTexts.add(spMunicipios2);
        listaEditTexts.add(spABM2);
        listaEditTexts.add(spGestores);
        listaEditTexts.add(et_pais);
        listaEditTexts.add(et_typeUser);
    }

    private void actualizarSpinnerGestores() {
        ArrayAdapter<Gestor> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaGestores);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGestores.setAdapter(arrayAdapter);
    }
    private void getAllGestores() {
         db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            listaGestores=new ArrayList<Gestor>();
                            Gestor gestorActual;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                gestorActual = documentSnapshot.toObject(Gestor.class);
                                listaGestores.add(gestorActual);
                                Log.d(getTag(), String.valueOf(listaGestores));
                            }
                            actualizarSpinnerGestores();
                        }else{
                            Log.d(getTag(), "ERROR OBTENIENDO DATA!", task.getException());
                            Toast.makeText(getContext(), "ERROR! No pudimos completar la lista de gestores", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void configuracionEventosListenersBotones() {
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearGestor();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarGestor();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarGestor();
            }
        });
    }

    private void crearGestor() {
        if (!validarCampos()) {
            Toast.makeText(getContext(), "Revise el formulario", Toast.LENGTH_LONG).show();
        } else {
            Gestor gestor = crearGestorConDatosFormulario();
            putGestor(gestor);
        }
    }

    private void putGestor(Gestor gestor) {
        db.collection("Users")
                .add(gestor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), R.string.msg_Alta_Ok, Toast.LENGTH_LONG).show();
                        Log.d(getTag(), "¡SE REGISTRO CON EXITO!");
                        gestor.setId(documentReference.getId());
                        limpiarFormulario();
                        listaGestores.add(gestor);
                        actualizarSpinnerGestores();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), R.string.msg_Alta_Error, Toast.LENGTH_LONG).show();
                        Log.d(getTag(), "OCURRIO UN ERROR: " + e.getMessage(), e);
                    }
                });
    }

    private void eliminarGestor() {
        Gestor eliminarGestor;
        eliminarGestor = (Gestor) spGestores.getSelectedItem();

        db.collection("Users")
                .document(eliminarGestor.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), R.string.msg_Baja_OK, Toast.LENGTH_LONG).show();
                        listaGestores.remove(eliminarGestor);
                        actualizarSpinnerGestores();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(getTag(), "onFailure: " + e.getMessage());
                        Toast.makeText(getContext(), R.string.msg_Baja_Error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void modificarGestor() {
        Gestor gestorFormulario = crearGestorConDatosFormulario();
        Gestor gestorSeleccionado = (Gestor) spGestores.getSelectedItem();

        gestorFormulario.setId(gestorSeleccionado.getId());

        //Me fijo si el gestor seleccionado en el Spinner es igual a la de los datos del formulario (por eso tuve que agregarle el id de la seleccionada a la creada a partir del form)
        if (gestorFormulario != gestorSeleccionado) {
            //valido campos
            if (validarCampos()) {
                db.collection("Users")
                        .document(gestorFormulario.getId())
                        .set(gestorFormulario)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), R.string.msg_Modificacion_Ok, Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(getTag(), e.getMessage());
                                Toast.makeText(getContext(), R.string.msg_Modificacion_Error, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } else {
            Toast.makeText(getContext(), "¡No ha modificado ningún campo!", Toast.LENGTH_LONG).show();
        }
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

    private Gestor crearGestorConDatosFormulario() {
        //DENTRO DEL CONSTRUCTOR VAN LOS CAMPOS OBLIGATORIOS. LOS QUE NO LO SON, VAN FUERA USANDO SETTERS.
        Gestor gestorNuevo;
        gestorNuevo = new Gestor(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                et_Nombre.getText().toString().trim(),
                et_email.getText().toString().trim(),
                et_contrasenia.getText().toString().trim(),
                et_pais.toString().trim(),
                et_typeUser.toString().trim(),
                spMunicipios2.getSelectedItem().toString().trim()
        );
        return gestorNuevo;
    }

    private void instanciarEditTextsFormulario(View v) {

        spMunicipios2 = v.findViewById(R.id.spMunicipios2);
        et_Nombre = v.findViewById(R.id.et_nombre);
        et_contrasenia = v.findViewById(R.id.et_contrasenia);
        et_email = v.findViewById(R.id.et_email);
        et_pais = v.findViewById(R.id.et_pais);
        et_typeUser = v.findViewById(R.id.et_typeUser);
    }

    //TODO ésto no se llama
    private void cargarFormularioItemSeleccionado() {
        Gestor gestorSeleccionado;
        gestorSeleccionado = (Gestor) spGestores.getSelectedItem();
        et_Nombre.setText(gestorSeleccionado.getNombre());
        et_contrasenia.setText(gestorSeleccionado.getPassword());
        try {
            spMunicipios2.setSelection(obtenerIndiceDelRecurso(gestorSeleccionado.getMunicipio(), getContext().getResources().getStringArray(R.array.MUNICIPIOS)));
        } catch (Exception e) {
            spMunicipios2.setSelection(0);
        }
        et_email.setText(gestorSeleccionado.getMail());
        et_pais.setText(gestorSeleccionado.getPais());
        et_typeUser.setText(gestorSeleccionado.getTypeUser());
    }

    private int obtenerIndiceDelRecurso(String valorCampo, String[] lista) {
        int i = 0;
        int indiceBuscado = -1;

        try {
            while (i < lista.length && indiceBuscado == -1) {
                if (valorCampo.equalsIgnoreCase(lista[i])) {
                    indiceBuscado = i;
                }
                i++;
            }

            return indiceBuscado;
        } catch (Exception e) {
            indiceBuscado = 0;
            return indiceBuscado;
        }
    }


    private void limpiarFormulario() {

        for (Object o : listaEditTexts) {
            if (o instanceof EditText) {
                ((EditText) o).setHintTextColor(getResources().getColor(R.color.default_hint));
                ((EditText) o).setText("");
            } else {
                ((Spinner) o).setBackgroundColor(getResources().getColor(R.color.default_hint));
                ((Spinner) o).setSelection(0);
            }
        }
    }

    private void flujoAlta() {
        scrollView.setVisibility(View.VISIBLE);
        formLinearLayout.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        spGestores.setVisibility(View.GONE);
        limpiarFormulario();
    }

    private void flujoModificacion() {
        scrollView.setVisibility(View.VISIBLE);
        formLinearLayout.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.VISIBLE);
        spGestores.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);

    }

    private void flujoBaja() {
        spGestores.setVisibility(View.VISIBLE);
        btnEliminar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        formLinearLayout.setVisibility(View.GONE);
        btnModificar.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);
    }

    private void cargarFormularioDeGestor(Gestor gestorSeleccionado) {

        if (!esMismoGestor(gestorSeleccionado)) {
            et_Nombre.setText(gestorSeleccionado.getNombre());
            try {
                spMunicipios2.setSelection(obtenerIndiceDelRecurso(gestorSeleccionado.getMunicipio(), getContext().getResources().getStringArray(R.array.MUNICIPIOS)));
            } catch (Exception e) {
                spMunicipios2.setSelection(0);
            }
            et_email.setText(gestorSeleccionado.getMail());
            et_contrasenia.setText(gestorSeleccionado.getPassword());
            et_pais.setText(gestorSeleccionado.getPais());
            et_typeUser.setText(gestorSeleccionado.getTypeUser());
        }
    }

    private boolean esMismoGestor(Gestor gestorSeleccionado) {
        return gestorSeleccionado.getNombre().equals(et_Nombre.getText().toString().trim()) && gestorSeleccionado.getMunicipio().equals(spMunicipios2.getSelectedItem().toString().trim());
    }
    private void actualizarInterfazProcesoCompleto() {
        //progressDialog.setProgress(100);
        //progressDialog.dismiss();
        limpiarFormulario();
        spABM2.setSelection(0); //flujo alta.
    }
    private Gestor obtenerUser() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String userString = sharedPreferences.getString("user", "");
        return gson.fromJson(userString, Gestor.class);
    }
}
