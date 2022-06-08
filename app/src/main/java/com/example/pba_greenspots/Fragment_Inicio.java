package com.example.pba_greenspots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pba_greenspots.fragments.RegisterFragment;
import com.example.pba_greenspots.fragments.ReservesFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Fragment_Inicio extends Fragment {
    View inicio;
    Button btnLogin, btnRegister;
    SignInButton btnGoogleSignIn;
    FragmentManager fragmentManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    public Fragment_Inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inicio = inflater.inflate(R.layout.fragment_inicio,container,false);

        fragmentManager=getParentFragmentManager();
        btnGoogleSignIn = inicio.findViewById(R.id.btnGoogleSignIn);
        btnLogin = inicio.findViewById(R.id.btnLogin);
        btnRegister = inicio.findViewById(R.id.btnRegister);


        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(gsc.getSignInIntent()));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFr_MainActivity, Fragment_LogIn.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFr_MainActivity, RegisterFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        gsc = GoogleSignIn.getClient(getContext(), gso);

        return inicio;
    }


    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    //succesful login
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account != null;

                    Toast.makeText(getContext(),"Inicio se sesion exitoso!", Toast.LENGTH_LONG);
                    //GUARDAR LOS DATOS CON SHAREDPREFERENCES

                    fragmentManager.beginTransaction()
                                .replace(R.id.navHostFr_MainActivity, Fragment_LogIn.class, null)
                            .addToBackStack(null)
                            .setReorderingAllowed(true)
                            .commit();


                } catch (ApiException e) {
                    e.printStackTrace();
//                    fragmentManager.beginTransaction()
//                                    .replace(R.id.navHostFr_MainActivity, Fragment_Inicio.class, null)
//                                    .setReorderingAllowed(true)
//                                    .addToBackStack(null)
//                                    .commit();

                    Toast.makeText(getContext(),"No se ha podido iniciar sesion!", Toast.LENGTH_LONG);
                    Log.d(getTag(), e.getMessage());
                }
            }
        }
    });

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (gAccount != null){
            fragmentManager.beginTransaction()
                    // SI ESTA LOGUEADO, LO MANDO AL HOME -  recycler
                    .replace(R.id.navHostFr_MainActivity, ReservesFragment.class, null )
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        }

    }
}