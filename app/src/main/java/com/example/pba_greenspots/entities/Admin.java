package com.example.pba_greenspots.entities;

public class Admin extends Gestor{
    public Admin(String municipio) {
        super(municipio);
    }

    public Admin(String id, String nombre, String mail, String password, String pais, String typeUser, String municipio) {
        super(id, nombre, mail, password, pais, typeUser, municipio);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }
}
