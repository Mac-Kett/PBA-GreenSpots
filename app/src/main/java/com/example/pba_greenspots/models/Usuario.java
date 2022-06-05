package com.example.pba_greenspots.models;

public class Usuario {

    private String nombre;
    private String mail;
    private String password;
    private String pais;

    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Usuario(String nombre, String mail, String password, String pais) {
        setNombre(nombre);
        setMail(mail);
        setPassword(password);
        setPais(pais);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
