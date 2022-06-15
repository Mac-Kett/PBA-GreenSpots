package com.example.pba_greenspots.entities;

public class Gestor extends Usuario{
    private String municipio;

    public Gestor(String municipio) {
        this.municipio = municipio;
    }

    public Gestor(String id, String nombre, String mail, String password, String pais, String typeUser, String municipio) {
        super(id, nombre, mail, password, pais, typeUser);
        this.municipio = municipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "municipio='" + municipio + '\'' +
                ", id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }
}
