package br.ufg.inf.quintacalendario.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
