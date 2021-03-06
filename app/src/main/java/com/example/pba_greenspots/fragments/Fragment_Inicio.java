package com.example.pba_greenspots.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pba_greenspots.NavigationActivity;
import com.example.pba_greenspots.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Inicio extends Fragment {
    View inicio;
    private Button btnLogin, btnRegister;
    //SignInButton btnGoogleSignIn;
    private FragmentManager fragmentManager;
    //private GoogleSignInOptions gso;
    //private GoogleSignInClient gsc;

    public Fragment_Inicio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (estaLogueado()){
            enviarAHome();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inicio = inflater.inflate(R.layout.fragment_inicio,container,false);

        fragmentManager=getParentFragmentManager();

        inicializarBotones();

        configurarOnClickListeners();

        //configurarGoogleSignIn();

        return inicio;
    }
    private void configurarOnClickListeners() {
//        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resultLauncher.launch(new Intent(gsc.getSignInIntent()));
//            }
//        });

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
    }

//    private void configurarGoogleSignIn() {
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestProfile()
//                .build();
//
//        gsc = GoogleSignIn.getClient(getContext(), gso);
//    }
    private void inicializarBotones() {
        //btnGoogleSignIn = inicio.findViewById(R.id.btnGoogleSignIn);
        btnLogin = inicio.findViewById(R.id.btnLogin);
        btnRegister = inicio.findViewById(R.id.btnRegister);
    }
    private void enviarAHome() {
        startActivity(new Intent(getActivity(), NavigationActivity.class));
        requireActivity().finish();
    }
    private boolean estaLogueado() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(requireContext());
        FirebaseUser mAuth = firebaseAuth.getCurrentUser();
        //return (gAccount != null || mAuth!=null);
        return mAuth!=null;
    }

//    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//
//            if (result.getResultCode() == Activity.RESULT_OK){
//                Intent intent = result.getData();
//
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
//                try {
//                    //succesful login
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    assert account != null;
//
//                    Toast.makeText(getContext(),"Inicio se sesion exitoso!", Toast.LENGTH_LONG).show();
//
//                    fragmentManager.beginTransaction()
//                                .replace(R.id.navHostFr_MainActivity, ReservesFragment.class, null)
//                            .addToBackStack(null)
//                            .setReorderingAllowed(true)
//                            .commit();
//
//                } catch (ApiException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(),"No se ha podido iniciar sesion!", Toast.LENGTH_LONG).show();
//                    Log.d(getTag(), e.getMessage());
//                }
//            }
//        }
//    });

}