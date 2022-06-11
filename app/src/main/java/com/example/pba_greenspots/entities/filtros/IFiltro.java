package com.example.pba_greenspots.entities.filtros;

import com.example.pba_greenspots.Reserve;

public interface IFiltro {
    boolean aplicarFiltro(Reserve reserve);

    boolean estaActivado();
}
