package com.example.pba_greenspots.entities.filtros;

import android.widget.SearchView;
import com.example.pba_greenspots.entities.Reserve;

import java.util.Locale;

public class FiltroNombreUnidad implements IFiltro{
    private SearchView searchView;

    public FiltroNombreUnidad(SearchView searchView){
        this.searchView = searchView;
    }

    @Override
    public boolean aplicarFiltro(Reserve reserve) {
        boolean resultado = true;
        String nombreUnidad= reserve.getNombreUnidad().toLowerCase(Locale.ROOT);
        if (!searchView.getQuery().toString().isEmpty()) {
            resultado = nombreUnidad.contains(searchView.getQuery().toString().toLowerCase(Locale.ROOT));
        }
        return resultado;
    }

    @Override
    public boolean estaActivado() {
        return true;
    }
}
