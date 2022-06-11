package com.example.pba_greenspots.entities.filtros;

import android.widget.SearchView;
import com.example.pba_greenspots.entities.Reserve;

public class FiltroNombreUnidad implements IFiltro{
    private SearchView searchView;

    public FiltroNombreUnidad(SearchView searchView){
        this.searchView = searchView;
    }

    @Override
    public boolean aplicarFiltro(Reserve reserve) {
        boolean resultado = true;
        String nombre= reserve.getNombreUnidad();
        if (!searchView.getQuery().toString().isEmpty()) {
            resultado = reserve.getNombreUnidad().equalsIgnoreCase(searchView.getQuery().toString());
        }
        return resultado;
    }

    @Override
    public boolean estaActivado() {
        return true;
    }
}
