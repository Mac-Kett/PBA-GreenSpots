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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pba_greenspots.fragments.Fragment_Gestores_ABM;
import com.example.pba_greenspots.fragments.Fragment_Reserves_ABM;
import com.example.pba_greenspots.fragments.PerfilUsuarioFragment;
import com.example.pba_greenspots.fragments.RegisterFragment;
import com.example.pba_greenspots.fragments.ReservesFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String ADMIN = "3";
    private final static String GESTOR = "2";
    private final static String TURISTA = "1";
    private String type;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private Menu menu;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String idUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //UI

        drawerLayout = findViewById(R.id.lay_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragments, new ReservesFragment()).commit();
        setTitle("Inicio");

        // setup toolbar
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        db = FirebaseFirestore.getInstance();
        // Consultar la DB con el currentUser para pedir el typeUser
        //user = firebaseAuth.getCurrentUser();
        //idUser = user.getUid();

        // Settear el type con typeUser
        type="1";

        // Usar SetMenuItem pasando por parametro el type para desbloquear los items segun corresponda
        setMenuItems(type);

        navigationView.setNavigationItemSelectedListener(this);

    }


    //Segun el type que llegue por parametro activa la visibilidad de los items ocultos
    private void setMenuItems(String type) {
        menu = navigationView.getMenu();
        switch (type){
            case GESTOR:
                MenuItem item = menu.findItem(R.id.abmreservas);
                item.setVisible(true);
                break;
            case ADMIN:
                MenuItem item2 = menu.findItem(R.id.abmgestores);
                item2.setVisible(true);
                break;
        }
    }

    //Consulta el id del item si es "logout" procede a cerrar la sesion y a cambiar de activity
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
//            startActivity(new Intent(this ,MainActivity.class));
//            this.finish();
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    // deja visible la "hamburguesa" si cambia de direccion la pantalla
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    //finaliza la activity y nos devuelve al main activity con su inicio de cesion
    private void signOutUser() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Cambia de fragmente segun el item que se seleccione en el menu
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
                ft.replace(R.id.content_fragments, new Fragment_Gestores_ABM()).commit();
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