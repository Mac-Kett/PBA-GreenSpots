package com.example.pba_greenspots.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pba_greenspots.NavigationActivity;
import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.Admin;
import com.example.pba_greenspots.entities.Gestor;
import com.example.pba_greenspots.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

public class Fragment_LogIn extends Fragment {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Button btnEntrar;
    private EditText etMail, etPass;
    private Usuario user;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public Fragment_LogIn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        gson = new Gson();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_log_in, container, false);
        etMail = v.findViewById(R.id.etMail);
        etPass = v.findViewById(R.id.etPass);
        btnEntrar = v.findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(this::loginFirebaseAuth);

        return v;
    }

    private void loginFirebaseAuth(View view){
        String email=etMail.getText().toString().trim();
        String password =etPass.getText().toString();

        if (validEmail(email)){
            if (!password.equals("")){
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                try {
                                    if (task.isSuccessful()) {
                                        obtenerUsuarioFirebaseFiresStore(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());

                                    } else {
                                        Toast.makeText(getContext(), "No se ha podido iniciar sesion!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e){
                                    Toast.makeText(getContext(), "Hubo un error. Contacte con el administrador.", Toast.LENGTH_LONG).show();
                                    Log.d(getTag(),"Las dos bases no están sincronizadas, chequear ok en firebaseAuth y fail en Firestore");
                                }
                            }
                        });
            }else{
                Toast.makeText(this.getContext(), "Ingrese contraseña!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this.getContext(), "Ingrese email valido!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void obtenerUsuarioFirebaseFiresStore(String id){

        db.collection("Users")
                .whereEqualTo("id", id)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        //si es succesfull si o si tuvo que haber encontrado al menos 1 item. El limit(1) hace que me retorne uno solo.
                        String typeUser = (String)task.getResult().getDocuments().get(0).get("typeUser");

                        switch (Objects.requireNonNull(typeUser)){
                            case "1":
                                user = task.getResult().getDocuments().get(0).toObject(Usuario.class);
                                break;
                            case "2":
                                user = task.getResult().getDocuments().get(0).toObject(Gestor.class);
                                break;
                            case "3":
                                user = task.getResult().getDocuments().get(0).toObject(Admin.class);
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String jsonUser = gson.toJson(user);
                        editor.putString("user", jsonUser).commit();
                        Toast.makeText(getContext(), "Bienvenido!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                        requireActivity().finish();
                    }else{
                        Log.d(getTag(), "No se ha podido completar la carga del usuario", null);
                    }
                });

        //ESTE METODO SIRVE SI EL Uid DE FIREBASEAUTH, EN EL REGISTRO, LO GUARDAN COMO ID DEL DOCUMENTO EN FIRESTORE.
        //        DocumentReference docRef = db.collection("Users").document(docId);
//        docRef.get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()){
//                            user = task.getResult().toObject(Usuario.class);
//                        } else{
//                            Log.d(getTag(), "No se ha podido completar la carga del usuario", null);
//                        }
//                    }
//                });

    }

/*  METODO INICIAL DE LOGIN. IGNORABA FIREBASEAUTH.
    private void login(View view){
        CollectionReference usersCol = FirebaseFirestore.getInstance().collection("Users");

        String mail=etMail.getText().toString();
        String pass =etPass.getText().toString();

        if (!mail.equals("")&&!pass.equals("")){
            usersCol.whereEqualTo("mail", mail)
                    .limit(1)
                    .whereEqualTo("password", pass)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().getDocuments().size()==1) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Usuario usuario = document.toObject(Usuario.class);
                                    Log.d("Fragment_LogIn", document.getId() + " => " + document.getData());
                                    Toast.makeText(getContext(), "Bienvenido!", Toast.LENGTH_LONG).show();
                                }
                                startActivity(new Intent(getActivity(), NavigationActivity.class));
                            } else{
                                Log.d("Fragment_LogIn", "No hay documento coincidente");
                                Toast.makeText(getContext(), "No se encontro!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Log.d("Fragment_LogIn", "Error getting documents: ", task.getException());
                        }
                    });
        }else{
            Toast.makeText(this.getContext(), "Ingrese ambos datos, por favor!", Toast.LENGTH_LONG).show();
        }
    }
*/

    public void onStart() {
        super.onStart();
    }


}