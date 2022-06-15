package com.example.pba_greenspots.entities.filtros;

import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.pba_greenspots.METODOS_COMPLEMENTARIOS;
import com.example.pba_greenspots.entities.Reserve;
import com.example.pba_greenspots.entities.Reserve;

public class FiltroTipoAdministracion extends FiltroSpinner{
    public FiltroTipoAdministracion(CheckBox checkBox, Spinner spinner) {
        super(checkBox, spinner);
    }

    @Override
    public boolean aplicarFiltro(Reserve reserva) {
        return reserva.getAdministracionPublicaPrivada() != null && reserva.getAdministracionPublicaPrivada().trim().equalsIgnoreCase(getSpinner().getSelectedItem().toString());
    }

    @Override
    void cargarSpinner() {
        METODOS_COMPLEMENTARIOS.completarSpinnerTipoAdministracion(getSpinner(), getSpinner().getContext());
    }
}
