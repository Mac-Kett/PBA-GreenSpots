package com.example.pba_greenspots.entities.filtros;

import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.Reserve;

public class FiltroZonaServicios extends FiltroSpinner{
    public FiltroZonaServicios(CheckBox checkBox, Spinner spinner) {
        super(checkBox, spinner);
    }

    @Override
    public boolean aplicarFiltro(Reserve reserva) {
        return reserva.getZonaServicios().equalsIgnoreCase(getSpinner().getSelectedItem().toString());
    }

    @Override
    void cargarSpinner() {
        METODOS_COMPLEMENTARIOS.completarSpinnerZonaServicios(getSpinner(), getSpinner().getContext());
    }
}
