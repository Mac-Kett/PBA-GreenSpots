package com.example.pba_greenspots;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class METODOS_COMPLEMENTARIOS {
    public static void completarSpinnerABM(Spinner spinnerABM, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.ABM, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerABM.setAdapter(adapter);

    }

    public static void completarSpinnerMunicipios(Spinner spinnerMunicipios, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.MUNICIPIOS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipios.setAdapter(adapter);

    }

    public static void completarSpinnerTipoAdministracion(Spinner spinnerTipoAdministracion, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.TIPO_ADMINISTRACION, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAdministracion.setAdapter(adapter);

    }

    public static void completarSpinnerCosto(Spinner spinnerCosto, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.COSTO, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCosto.setAdapter(adapter);

    }

    public static void completarSpinnerNivelesDificultad(Spinner spinnerNivelesDificultad, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.NIVELES_DIFICULTAD, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNivelesDificultad.setAdapter(adapter);

    }

    public static void completarSpinnerSenializacionServicios(Spinner spinnerSenializacion, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.SENIALIZACION_DE_SENDEROS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSenializacion.setAdapter(adapter);

    }

    public static void completarSpinnerZonaServicios(Spinner spinnerZonaServicios, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.ZONA_DE_SERVICIOS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonaServicios.setAdapter(adapter);

    }

    public static void completarSpinnerPaises(Spinner spinnerPaises, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.PAISES, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaises.setAdapter(adapter);

    }
}
