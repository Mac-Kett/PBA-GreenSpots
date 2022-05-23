package com.example.pba_greenspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

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
               Registro registro = new Registro();
               FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.fragmentContainerView,registro);
               fragmentTransaction.commit();


            }
        });
    }


}