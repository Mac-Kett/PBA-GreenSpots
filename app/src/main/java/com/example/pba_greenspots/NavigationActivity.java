package com.example.pba_greenspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.pba_greenspots.databinding.ActivityMainBinding;

import com.example.pba_greenspots.fragments.HomeFragment;
import com.example.pba_greenspots.fragments.PerfilUsuarioFragment;

import com.example.pba_greenspots.fragments.RegisterFragment;
import com.example.pba_greenspots.fragments.ReservesFragment;
import com.example.pba_greenspots.fragments.SearchFragment;


public class NavigationActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new ReservesFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.account:
                    replaceFragment(new PerfilUsuarioFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }

}