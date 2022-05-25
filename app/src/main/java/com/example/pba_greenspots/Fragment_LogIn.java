package com.example.pba_greenspots;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_LogIn#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Fragment_LogIn extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText etMail;
    private EditText etPass;
    private Button btn1;

    public Fragment_LogIn() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_LogIn newInstance(EditText etMail, EditText etPass, Button btn1) {
        Fragment_LogIn fragment = new Fragment_LogIn();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth auth;
    //FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        //Inicializo Auth de Firebase
        auth = FirebaseAuth.getInstance();

        //Inicializo las variables
        etMail = view.findViewById(R.id.etMail);
        etPass = view.findViewById(R.id.etPass);
        btn1 = view.findViewById(R.id.btn1);

        //Pongo el button a la espera
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Los paso a String porque es el tipo de datos que espera la DB
                String mail = etMail.getText().toString();
                String pass = etPass.getText().toString();

                //Auth hace su magia
                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d("LogIn", "LogIn Successful");
                        Toast.makeText(getActivity(), "LogIn Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("LogIn", "LogIn Failed");
                        Toast.makeText(getActivity(), "LogIn Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(mail).child("Password").setValue(pass);
            }
        });
        return view;
    }
}