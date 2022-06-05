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
}
