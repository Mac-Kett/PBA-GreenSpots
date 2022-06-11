package com.example.pba_greenspots.entities.filtros;

import android.widget.SearchView;

import com.example.pba_greenspots.Reserve;

public class FiltroNombreUnidad implements IFiltro{
    private String textoBuscador;

    public FiltroNombreUnidad(String textoBuscador){
        this.textoBuscador = textoBuscador;
    }

    @Override
    public boolean aplicarFiltro(Reserve reserve) {
        boolean resultado = true;
        String nombre= reserve.getNombreUnidad();
        if (!textoBuscador.isEmpty()) {
            resultado = reserve.getNombreUnidad().equalsIgnoreCase(textoBuscador);
        }
        return resultado;
    }

    @Override
    public boolean estaActivado() {
        return true;
    }
}
