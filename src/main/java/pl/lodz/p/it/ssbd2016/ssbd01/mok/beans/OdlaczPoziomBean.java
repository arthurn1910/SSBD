/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

/**
 *
 * @author rpawlaczyk
 */

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OdlaczPoziomBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    /***
     * Metoda ustawiajaca uzytkowniksession
     * @param uzytkownikSession 
     */
    public void setUzytkownikSession(UzytkownikSession uzytkownikSession) {
        this.uzytkownikSession = uzytkownikSession;
    }
    
    /***
     * Metoda wywołuje funkcje odlaczPoziomAgent w uzytkownikSession oraz metode initModel()
     */
    public void odlaczPoziomAgent(){
        uzytkownikSession.odlaczPoziomAgent();
    }
    /***
     * Metoda wywołuje funkcje odlaczPoziomMenadzer w uzytkownikSession oraz metode initModel()
     */
    public void odlaczPoziomMenadzer(){
        uzytkownikSession.odlaczPoziomMenadzer();
    }
    /***
     * Metoda wywołuje funkcje odlaczPoziomAdministrator w uzytkownikSession oraz metode initModel()
     */
    public void odlaczPoziomAdministrator(){
        uzytkownikSession.odlaczPoziomAdministrator();
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
     * Funkcja sprawdza czy konto posiada poziom Administrator
     * @return 
     */
    public Boolean sprawdzPoziomAdministrator(){
        return uzytkownikSession.sprawdzPoziomAdministrator();
    }
     
}
