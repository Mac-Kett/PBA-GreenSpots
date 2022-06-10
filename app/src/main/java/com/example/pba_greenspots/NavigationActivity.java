package com.example.pba_greenspots;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pba_greenspots.fragments.Fragment_Reserves_ABM;
import com.example.pba_greenspots.fragments.PerfilUsuarioFragment;
import com.example.pba_greenspots.fragments.RegisterFragment;
import com.example.pba_greenspots.fragments.ReservesFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //UI
        drawerLayout = findViewById(R.id.lay_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.content_fragments, new ReservesFragment()).commit();
        setTitle("Inicio");
        // setup toolbar
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String aux1 = getString(item.getItemId());
        String aux2 ="logout";
        firebaseAuth = FirebaseAuth.getInstance();
        user =  firebaseAuth.getCurrentUser();

        if (!aux1.equals(aux2) ){
            selectedItemNav(item);
        }else{
            //vamos a cerrar sesion
            firebaseAuth.signOut();
            //vamos a cambiar de activity
            signOutUser();
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void signOutUser() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void selectedItemNav(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()){
            case R.id.home:
                ft.replace(R.id.content_fragments, new ReservesFragment()).commit();
                break;
            case R.id.search:
                ft.replace(R.id.content_fragments, new RegisterFragment()).commit();
                break;
            case R.id.account:
                ft.replace(R.id.content_fragments, new PerfilUsuarioFragment()).commit();
                break;
            case R.id.abmreservas:
                ft.replace(R.id.content_fragments, new Fragment_Reserves_ABM()).commit();
                break;
            case R.id.abmgestores:
                ft.replace(R.id.content_fragments, new Fragment_Reserves_ABM()).commit();
                break;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}