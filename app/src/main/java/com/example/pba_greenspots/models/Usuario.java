package com.example.pba_greenspots.models;

public class Usuario {

    private String mail;
    private String password;
    private String tipoUser;

    public Usuario(){

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

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public Usuario(String mail, String password, String tipoUser) {
        setMail(mail);
        setPassword(password);
        setTipoUser(tipoUser);
    }


    @Override
    public String toString() {
        return "Usuario{" +

                ", email='" + mail + '\'' +
                ", contraseña='" + password + '\'' +

                '}';
    }
}
