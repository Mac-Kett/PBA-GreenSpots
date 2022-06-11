package com.example.pba_greenspots.entities.filtros;

import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.Reserve;

public class FiltroDificultadSenderismo extends FiltroSpinner{
    public FiltroDificultadSenderismo(CheckBox checkBox, Spinner spinner) {
        super(checkBox, spinner);
    }

    @Override
    public boolean aplicarFiltro(Reserve reserva) {
        return reserva.getDificultadSenderismo() != null && reserva.getDificultadSenderismo().trim().equalsIgnoreCase(getSpinner().getSelectedItem().toString());
    }

    @Override
    void cargarSpinner() {
        METODOS_COMPLEMENTARIOS.completarSpinnerNivelesDificultad(getSpinner(), getSpinner().getContext());
    }
}
