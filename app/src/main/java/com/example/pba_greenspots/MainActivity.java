package com.example.pba_greenspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pba_greenspots.fragments.MiPerfil;
import com.example.pba_greenspots.fragments.RegisterFragment;


public class MainActivity extends AppCompatActivity {
    private Button btnComenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnComenzar = (Button) findViewById(R.id.btnComenzar);

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RegisterFragment register = new RegisterFragment();
                //RegisterFragment register2 = new RegisterFragment();
                //MiPerfil miPerfil = new MiPerfil();
               FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();
              // fragmentTransaction.replace(R.id.fragment_container, miPerfil);
                fragmentTransaction.replace(R.id.fragment_container, register);
               // fragmentTransaction.replace(R.id.fragment_container, register2);
               fragmentTransaction.commit();


            }
        });
    }




}