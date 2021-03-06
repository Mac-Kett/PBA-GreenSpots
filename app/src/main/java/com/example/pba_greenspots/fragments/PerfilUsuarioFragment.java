package com.example.pba_greenspots.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.pba_greenspots.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class PerfilUsuarioFragment extends Fragment {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView fullname, pais, mail, password;
    private FirebaseUser user;
    private String idUser;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        fullname = v.findViewById(R.id.textViewFullname);
        pais = v.findViewById(R.id.textViewCountry);
        mail = v.findViewById(R.id.textViewEmail);
        password = v.findViewById(R.id.textViewPassword);
        user =  firebaseAuth.getCurrentUser();
        idUser = user.getUid();
        //  Log.i("id usuario: ",idUser.toString());
        // ESTA LLEGANDO EL ID DEL USUARIO, NO EL DEL DOCUMENTO

        // idUser= "uLUvRr66zLry5357udoS";
        // forzamos la consulta a la db por un id para comprobar que se traen los datos pedidos

        db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot docSnapshot) {
                if (docSnapshot.exists()) {
                    fullname.setText(docSnapshot.getString("nombre"));
                    pais.setText(docSnapshot.getString("pais"));
                    mail.setText(docSnapshot.getString("mail"));
                    password.setText(docSnapshot.getString("password"));
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }



}