/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpoint;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
/**
 * Klasa ta jest wykorzystywana do logowania użytkownika do systemu
 * @author rpawlaczyk
 */
@Named
@RequestScoped
public class LogowanieBean {
    @EJB
    private MOKEndpointLocal MOKEndpoint;
    
    private String login;
    private String haslo;
    /**
     * Funkcja pobierająca login
     * @return 
     */
    public String getLogin() {
        return login;
    }
    /***
     * Funkcja pobierająca hasło
     * @return 
     */
    public String getHaslo() {
        return haslo;
    }
    
    /***
     * Metoda ustawiająca login
     * @param login 
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /***
     * Metoda ustawiająca hasło
     * @param haslo 
     */
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    /***
     * Funkcja przekazująca funkcji zaloguj w uzytkownikSession parametry login,haslo zwracająca Stringa z odpowiednim widokiem
     * @return 
     */
    public String zalogujKlienta(){
        if(MOKEndpoint.zaloguj(login,haslo)==true)
            return "zalogowano";
        return "errorLogin";
    }

}
