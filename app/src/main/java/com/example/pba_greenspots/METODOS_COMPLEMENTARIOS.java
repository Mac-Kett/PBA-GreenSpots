package com.example.pba_greenspots;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class METODOS_COMPLEMENTARIOS {
    public static Spinner completarSpinnerABM(Spinner spinnerABM, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.ABM, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerABM.setAdapter(adapter);

        return spinnerABM;
    }

    public static Spinner completarSpinnerMunicipios(Spinner spinnerMunicipios, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.municipios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipios.setAdapter(adapter);

        return spinnerMunicipios;
    }

    public static Spinner completarSpinnerTipoAdministracion(Spinner spinnerTipoAdministracion, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.TIPO_ADMINISTRACION, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAdministracion.setAdapter(adapter);

        return spinnerTipoAdministracion;
    }

    public static Spinner completarSpinnerCosto(Spinner spinnerCosto, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.COSTO, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCosto.setAdapter(adapter);

        return spinnerCosto;
    }

    public static Spinner completarSpinnerNivelesDificultad(Spinner spinnerNivelesDificultad, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.NIVELES_DIFICULTAD, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNivelesDificultad.setAdapter(adapter);

        return spinnerNivelesDificultad;
    }

    public static Spinner completarSpinnerSenializacionServicios(Spinner spinnerSenializacion, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.SENIALIZACION_DE_SENDEROS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSenializacion.setAdapter(adapter);

        return spinnerSenializacion;
    }
}
