package com.example.pba_greenspots.entities;

public class Usuario {

    protected String id;
    protected String nombre;
    protected String mail;
    protected String password;
    protected String pais;
    protected String typeUser;

    public Usuario(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public Usuario(String id, String nombre, String mail, String password, String pais, String typeUser) {
        setId(id);
        setNombre(nombre);
        setMail(mail);
        setPassword(password);
        setPais(pais);
        setTypeUser(typeUser);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                "nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
