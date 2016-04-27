/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

/**
 * Klasa ta jest wykorzystywana do dołaczenai i sprawdzenia poziomów dostępu użytkownika
 * @author rpawlaczyk
 */

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DolaczPoziomBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    /***
     * Metoda ustawiajaca uzytkownikSession
     * @param uzytkownikSession 
     */
    public void setUzytkownikSession(UzytkownikSession uzytkownikSession) {
        this.uzytkownikSession = uzytkownikSession;
    }
    
    /***
     * Metoda wywołuje funkcje odlaczPoziomAgent w uzytkownikSession oraz metode initModel()
     */
    public void dolaczPoziomAgent(){
        uzytkownikSession.dolaczPoziomAgent();
    }
    /***
     * Metoda wywołuje funkcje odlaczPoziomMenadzer w uzytkownikSession oraz metode initModel()
     */
    public void dolaczPoziomMenadzer(){
        uzytkownikSession.dolaczPoziomMenadzer();
    }
    /***
     * Metoda wywołuje funkcje odlaczPoziomAdministrator w uzytkownikSession oraz metode initModel()
     */
    public void dolaczPoziomAdministrator(){
        uzytkownikSession.dolaczPoziomAdministrator();
    }
    /***
     * Funkcja sprawdza czy konto posiada poziom Agent
     * @return 
     */
    public Boolean sprawdzPoziomAgent(){
        return uzytkownikSession.sprawdzPoziomAgent();

    }
    /***
     * Funkcja sprawdza czy konto posiada poziom Menadzer
     * @return 
     */
    public Boolean sprawdzPoziomMenadzer(){
        return uzytkownikSession.sprawdzPoziomMenadzer();
    }
    /***
     * Funkcja sprawdza czy konto posiada poziom Adminstrator
     * @return 
     */
    public Boolean sprawdzPoziomAdministrator(){
        return uzytkownikSession.sprawdzPoziomAdministrator();
    }
     
}
