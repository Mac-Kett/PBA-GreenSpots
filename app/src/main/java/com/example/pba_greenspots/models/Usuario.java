package com.example.pba_greenspots.models;

public class Usuario {

   // private String nombreCompleto;
    private String mail;
    private String password;
    private int tipoUser;
   // private String pais;

    public Usuario(){

    }

    public Usuario(String mail, String password, int tipoUser) {
        //setNombreCompleto(nombreCompleto);
        setMail(mail);
        this.password = password;
        this.tipoUser = tipoUser;
       // setContrasena(contrasena);
        //setPais(pais);
    }

    /*public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }*/


    public String getmail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

   /* public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }*/

    @Override
    public String toString() {
        return "Usuario{" +

                ", email='" + mail + '\'' +
                ", contraseña='" + password + '\'' +

                '}';
    }
}
