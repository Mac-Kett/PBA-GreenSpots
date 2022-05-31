package com.example.pba_greenspots;

import java.util.Map;

public class ReservaNatural {
    private String id;
    private String nombreUnidad;
    private String instrumentoPlanificacion;
    private String municipio;

    public ReservaNatural(){

    }

    public ReservaNatural(String nombreUnidad, String instrumentoPlanificaicon, String municipio) {
        this.nombreUnidad = nombreUnidad;
        this.instrumentoPlanificacion = instrumentoPlanificaicon;
        this.municipio = municipio;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getInstrumentoPlanificacion() {
        return instrumentoPlanificacion;
    }
    public void setInstrumentoPlanificacion(String instrumentoPlanificacion) {
        this.instrumentoPlanificacion = instrumentoPlanificacion;
    }
    public String getNombreUnidad() {
        return nombreUnidad;
    }
    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }
    public String getMunicipio() {
        return municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }


    //Este metodo permite que el Spinner que carga las reservas muestre solo el nombreUnidad.
    public String toString(){
        return this.getNombreUnidad();
    }

}
