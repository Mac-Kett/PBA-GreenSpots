package com.example.pba_greenspots.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pba_greenspots.MainActivity;
import com.example.pba_greenspots.NavigationActivity;
import com.example.pba_greenspots.R;
import com.example.pba_greenspots.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Fragment_LogIn extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button btn1;
    private EditText etMail, etPass;

    public Fragment_LogIn() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_log_in, container, false);
        etMail = (EditText) v.findViewById(R.id.etMail);
        etPass = (EditText) v.findViewById(R.id.etPass);
        btn1 = (Button) v.findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

        return v;
    }

    private void login(View view){
        CollectionReference usersCol = FirebaseFirestore.getInstance().collection("Users");

        String mail=etMail.getText().toString();
        String pass =etPass.getText().toString();

        if (!mail.equals("")&&!pass.equals("")){
            usersCol.whereEqualTo("mail", mail)
                    .limit(1)
                    .whereEqualTo("password", pass)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                        }
                    });
        }else{
            Toast.makeText(this.getContext(), "Ingrese ambos datos, por favor!", Toast.LENGTH_LONG).show();
        }
    }

    public void onStart() {
        super.onStart();
    }


}