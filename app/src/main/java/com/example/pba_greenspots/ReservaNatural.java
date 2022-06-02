package com.example.pba_greenspots;

import java.util.HashMap;
import java.util.Map;

public class ReservaNatural {


    private String id;
    private String nombreUnidad;
    private String instrumentoPlanificacion;
    private String municipio;
    private String et_administracionPublicaPrivada;
    private String et_zonaServicios;
    private String et_ingresoGratuitoPago;
    private String et_dificultadSenderismo;
    private String et_senializacion;
    private String et_accesos;
    private String et_horarios;
    private String et_informacionAdicional;
    private String et_gestionesDesarrollo;
    private String et_telefono;
    private String et_correoPagWeb;
    private String et_infraestructura;
    private String et_actividadesDelArea;
    private String et_fauna;
    private String et_flora;
    private String et_clima;
    private String et_geologia;
    private String et_superficie;
    private String et_geolocalizacion;
    private String et_caracteristicasGenerales;
    private String et_fechaCreacion;
    private String et_importancia;
    private String et_instrumentoLegal;
    private String et_acceso;

    public ReservaNatural(){}

    public ReservaNatural(String nombreUnidad,
                          String instrumentoPlanificaicon,
                          String municipio,
                          String et_administracionPublicaPrivada,
                          String et_zonaServicios,
                          String et_ingresoGratuitoPago,
                          String et_dificultadSenderismo,
                          String et_senializacion,
                          String et_accesos,
                          String et_horarios,
                          String et_informacionAdicional,
                          String et_gestionesDesarrollo,
                          String et_telefono,
                          String et_correoPagWeb,
                          String et_infraestructura,
                          String et_actividadesDelArea,
                          String et_fauna,
                          String et_flora,
                          String et_clima,
                          String et_geologia,
                          String et_superficie,
                          String et_geolocalizacion,
                          String et_caracteristicasGenerales,
                          String et_fechaCreacion,
                          String et_importancia,
                          String et_instrumentoLegal,
                          String et_acceso) {
        this.nombreUnidad = nombreUnidad;
        this.instrumentoPlanificacion = instrumentoPlanificaicon;
        this.municipio = municipio;
        this.et_administracionPublicaPrivada = et_administracionPublicaPrivada;
        this.et_zonaServicios = et_zonaServicios;
        this.et_ingresoGratuitoPago = et_ingresoGratuitoPago;
        this.et_dificultadSenderismo = et_dificultadSenderismo;
        this.et_senializacion = et_senializacion;
        this.et_accesos = et_accesos;
        this.et_horarios = et_horarios;
        this.et_informacionAdicional = et_informacionAdicional;
        this.et_gestionesDesarrollo = et_gestionesDesarrollo;
        this.et_telefono = et_telefono;
        this.et_correoPagWeb = et_correoPagWeb;
        this.et_infraestructura = et_infraestructura;
        this.et_actividadesDelArea = et_actividadesDelArea;
        this.et_fauna = et_fauna;
        this.et_flora = et_flora;
        this.et_clima = et_clima;
        this.et_geologia = et_geologia;
        this.et_superficie = et_superficie;
        this.et_geolocalizacion = et_geolocalizacion;
        this.et_caracteristicasGenerales = et_caracteristicasGenerales;
        this.et_fechaCreacion = et_fechaCreacion;
        this.et_importancia = et_importancia;
        this.et_instrumentoLegal = et_instrumentoLegal;
        this.et_acceso = et_acceso;

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

    public String getEt_administracionPublicaPrivada() {
        return et_administracionPublicaPrivada;
    }

    public void setEt_administracionPublicaPrivada(String et_administracionPublicaPrivada) {
        this.et_administracionPublicaPrivada = et_administracionPublicaPrivada;
    }

    public String getEt_zonaServicios() {
        return et_zonaServicios;
    }

    public void setEt_zonaServicios(String et_zonaServicios) {
        this.et_zonaServicios = et_zonaServicios;
    }

    public String getEt_ingresoGratuitoPago() {
        return et_ingresoGratuitoPago;
    }

    public void setEt_ingresoGratuitoPago(String et_ingresoGratuitoPago) {
        this.et_ingresoGratuitoPago = et_ingresoGratuitoPago;
    }

    public String getEt_dificultadSenderismo() {
        return et_dificultadSenderismo;
    }

    public void setEt_dificultadSenderismo(String et_dificultadSenderismo) {
        this.et_dificultadSenderismo = et_dificultadSenderismo;
    }

    public String getEt_senializacion() {
        return et_senializacion;
    }

    public void setEt_senializacion(String et_senializacion) {
        this.et_senializacion = et_senializacion;
    }

    public String getEt_accesos() {
        return et_accesos;
    }

    public void setEt_accesos(String et_accesos) {
        this.et_accesos = et_accesos;
    }

    public String getEt_horarios() {
        return et_horarios;
    }

    public void setEt_horarios(String et_horarios) {
        this.et_horarios = et_horarios;
    }

    public String getEt_informacionAdicional() {
        return et_informacionAdicional;
    }

    public void setEt_informacionAdicional(String et_informacionAdicional) {
        this.et_informacionAdicional = et_informacionAdicional;
    }

    public String getEt_gestionesDesarrollo() {
        return et_gestionesDesarrollo;
    }

    public void setEt_gestionesDesarrollo(String et_gestionesDesarrollo) {
        this.et_gestionesDesarrollo = et_gestionesDesarrollo;
    }

    public String getEt_telefono() {
        return et_telefono;
    }

    public void setEt_telefono(String et_telefono) {
        this.et_telefono = et_telefono;
    }

    public String getEt_correoPagWeb() {
        return et_correoPagWeb;
    }

    public void setEt_correoPagWeb(String et_correoPagWeb) {
        this.et_correoPagWeb = et_correoPagWeb;
    }

    public String getEt_infraestructura() {
        return et_infraestructura;
    }

    public void setEt_infraestructura(String et_infraestructura) {
        this.et_infraestructura = et_infraestructura;
    }

    public String getEt_actividadesDelArea() {
        return et_actividadesDelArea;
    }

    public void setEt_actividadesDelArea(String et_actividadesDelArea) {
        this.et_actividadesDelArea = et_actividadesDelArea;
    }

    public String getEt_fauna() {
        return et_fauna;
    }

    public void setEt_fauna(String et_fauna) {
        this.et_fauna = et_fauna;
    }

    public String getEt_flora() {
        return et_flora;
    }

    public void setEt_flora(String et_flora) {
        this.et_flora = et_flora;
    }

    public String getEt_clima() {
        return et_clima;
    }

    public void setEt_clima(String et_clima) {
        this.et_clima = et_clima;
    }

    public String getEt_geologia() {
        return et_geologia;
    }

    public void setEt_geologia(String et_geologia) {
        this.et_geologia = et_geologia;
    }

    public String getEt_superficie() {
        return et_superficie;
    }

    public void setEt_superficie(String et_superficie) {
        this.et_superficie = et_superficie;
    }

    public String getEt_geolocalizacion() {
        return et_geolocalizacion;
    }

    public void setEt_geolocalizacion(String et_geolocalizacion) {
        this.et_geolocalizacion = et_geolocalizacion;
    }

    public String getEt_caracteristicasGenerales() {
        return et_caracteristicasGenerales;
    }

    public void setEt_caracteristicasGenerales(String et_caracteristicasGenerales) {
        this.et_caracteristicasGenerales = et_caracteristicasGenerales;
    }

    public String getEt_fechaCreacion() {
        return et_fechaCreacion;
    }

    public void setEt_fechaCreacion(String et_fechaCreacion) {
        this.et_fechaCreacion = et_fechaCreacion;
    }

    public String getEt_importancia() {
        return et_importancia;
    }

    public void setEt_importancia(String et_importancia) {
        this.et_importancia = et_importancia;
    }

    public String getEt_instrumentoLegal() {
        return et_instrumentoLegal;
    }

    public void setEt_instrumentoLegal(String et_instrumentoLegal) {
        this.et_instrumentoLegal = et_instrumentoLegal;
    }

    public String getEt_acceso() {
        return et_acceso;
    }

    public void setEt_acceso(String et_acceso) {
        this.et_acceso = et_acceso;
    }

    //Este metodo permite que el Spinner que carga las reservas muestre solo el nombreUnidad.
    public String toString(){
        return this.getNombreUnidad();
    }

}
