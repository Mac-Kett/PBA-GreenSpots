package com.example.pba_greenspots.entities;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;

import com.google.protobuf.LazyStringArrayList;
import com.google.rpc.context.AttributeContext;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class Reserve implements Serializable {


    private String id;
    private String nombreUnidad;
    private String instrumentoPlanificacion;
    private String municipio;
    private String administracionPublicaPrivada;
    private String zonaServicios;
    private String ingresoGratuitoPago;
    private String dificultadSenderismo;
    private String senializacion;
    private String accesos;
    private String horarios;
    private String informacionAdicional;
    private String gestionesDesarrollo;
    private String telefono;
    private String correoPagWeb;
    private String infraestructura;
    private String actividadesDelArea;
    private String fauna;
    private String flora;
    private String clima;
    private String geologia;
    private String superficie;
    private String geolocalizacion;
    private String caracteristicasGenerales;
    private String fechaCreacion;
    private String importancia;
    private String instrumentoLegal;
    private String acceso;
    private String personal;
    private String imagen_Uno, imagen_Dos, imagen_Tres, imagen_Cuatro, imagen_Cinco, imagen_Seis, imagen_Siete;

    public Reserve(){}

    public Reserve(String nombreUnidad,
                   String instrumentoPlanificaicon,
                   String municipio,
                   String administracionPublicaPrivada,
                   String zonaServicios,
                   String ingresoGratuitoPago,
                   String dificultadSenderismo,
                   String senializacion,
                   String accesos,
                   String horarios,
                   String informacionAdicional,
                   String gestionesDesarrollo,
                   String telefono,
                   String correoPagWeb,
                   String infraestructura,
                   String actividadesDelArea,
                   String fauna,
                   String flora,
                   String clima,
                   String geologia,
                   String superficie,
                   String geolocalizacion,
                   String caracteristicasGenerales,
                   String fechaCreacion,
                   String importancia,
                   String instrumentoLegal,
                   String acceso,
                   String personal
                          ) {
        this.nombreUnidad = nombreUnidad;
        this.instrumentoPlanificacion = instrumentoPlanificaicon;
        this.municipio = municipio;
        this.administracionPublicaPrivada = administracionPublicaPrivada;
        this.zonaServicios = zonaServicios;
        this.ingresoGratuitoPago = ingresoGratuitoPago;
        this.dificultadSenderismo = dificultadSenderismo;
        this.senializacion = senializacion;
        this.accesos = accesos;
        this.horarios = horarios;
        this.informacionAdicional = informacionAdicional;
        this.gestionesDesarrollo = gestionesDesarrollo;
        this.telefono = telefono;
        this.correoPagWeb = correoPagWeb;
        this.infraestructura = infraestructura;
        this.actividadesDelArea = actividadesDelArea;
        this.fauna = fauna;
        this.flora = flora;
        this.clima = clima;
        this.geologia = geologia;
        this.superficie = superficie;
        this.geolocalizacion = geolocalizacion;
        this.caracteristicasGenerales = caracteristicasGenerales;
        this.fechaCreacion = fechaCreacion;
        this.importancia = importancia;
        this.instrumentoLegal = instrumentoLegal;
        this.acceso = acceso;
        this.personal = personal;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getInstrumentoPlanificacion() {
        return instrumentoPlanificacion;
    }

    public void setInstrumentoPlanificacion(String instrumentoPlanificacion) {
        this.instrumentoPlanificacion = instrumentoPlanificacion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getAdministracionPublicaPrivada() {
        return administracionPublicaPrivada;
    }

    public void setAdministracionPublicaPrivada(String administracionPublicaPrivada) {
        this.administracionPublicaPrivada = administracionPublicaPrivada;
    }

    public String getZonaServicios() {
        return zonaServicios;
    }

    public void setZonaServicios(String zonaServicios) {
        this.zonaServicios = zonaServicios;
    }

    public String getIngresoGratuitoPago() {
        return ingresoGratuitoPago;
    }

    public void setIngresoGratuitoPago(String ingresoGratuitoPago) {
        this.ingresoGratuitoPago = ingresoGratuitoPago;
    }

    public String getDificultadSenderismo() {
        return dificultadSenderismo;
    }

    public void setDificultadSenderismo(String dificultadSenderismo) {
        this.dificultadSenderismo = dificultadSenderismo;
    }

    public String getSenializacion() {
        return senializacion;
    }

    public void setSenializacion(String senializacion) {
        this.senializacion = senializacion;
    }

    public String getAccesos() {
        return accesos;
    }

    public void setAccesos(String accesos) {
        this.accesos = accesos;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getGestionesDesarrollo() {
        return gestionesDesarrollo;
    }

    public void setGestionesDesarrollo(String gestionesDesarrollo) {
        this.gestionesDesarrollo = gestionesDesarrollo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoPagWeb() {
        return correoPagWeb;
    }

    public void setCorreoPagWeb(String correoPagWeb) {
        this.correoPagWeb = correoPagWeb;
    }

    public String getInfraestructura() {
        return infraestructura;
    }

    public void setInfraestructura(String infraestructura) {
        this.infraestructura = infraestructura;
    }

    public String getActividadesDelArea() {
        return actividadesDelArea;
    }

    public void setActividadesDelArea(String actividadesDelArea) {
        this.actividadesDelArea = actividadesDelArea;
    }

    public String getFauna() {
        return fauna;
    }

    public void setFauna(String fauna) {
        this.fauna = fauna;
    }

    public String getFlora() {
        return flora;
    }

    public void setFlora(String flora) {
        this.flora = flora;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getGeologia() {
        return geologia;
    }

    public void setGeologia(String geologia) {
        this.geologia = geologia;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(String geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public String getCaracteristicasGenerales() {
        return caracteristicasGenerales;
    }

    public void setCaracteristicasGenerales(String caracteristicasGenerales) {
        this.caracteristicasGenerales = caracteristicasGenerales;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getInstrumentoLegal() {
        return instrumentoLegal;
    }

    public void setInstrumentoLegal(String instrumentoLegal) {
        this.instrumentoLegal = instrumentoLegal;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    //Este metodo permite que el Spinner que carga las reservas muestre solo el nombreUnidad.
    public String toString(){
        return this.getNombreUnidad();
    }

    public boolean validarAtributo(String avalidar, String[] stringArray){
        boolean result=false;
        int i=0;
        while(i<stringArray.length && !result){
                if(avalidar.equalsIgnoreCase(stringArray[i])){
                    result=true;
                }
                i++;
        }
        return result;
    }

}
