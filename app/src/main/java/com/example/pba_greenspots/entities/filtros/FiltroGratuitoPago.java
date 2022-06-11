package com.example.pba_greenspots.entities.filtros;

import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.entities.Reserve;

public class FiltroGratuitoPago extends FiltroSpinner{
    public FiltroGratuitoPago(CheckBox checkBox, Spinner spinner) {
        super(checkBox, spinner);
    }

    @Override
    public boolean aplicarFiltro(Reserve reserva) {
        return reserva.getIngresoGratuitoPago() != null && reserva.getIngresoGratuitoPago().trim().equalsIgnoreCase(getSpinner().getSelectedItem().toString());
    }

    @Override
    void cargarSpinner() {
        METODOS_COMPLEMENTARIOS.completarSpinnerCosto(getSpinner(), getSpinner().getContext());
    }
}
