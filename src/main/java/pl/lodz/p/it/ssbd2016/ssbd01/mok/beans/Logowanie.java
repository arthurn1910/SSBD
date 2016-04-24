/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
/**
 *
 * @author rpawlaczyk
 */
@Named
@RequestScoped
public class Logowanie {

    private String login;
    private String haslo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    
    public String zalogujKlienta(){
        if(false!=false)
            return "errorLogin";
        else
            return "zalogowano";
    }

}
