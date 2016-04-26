/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author rpawlaczyk
 */
@Named
@RequestScoped
public class WyswietlInformacje {
    @Inject
    private UzytkownikSession uzytkownikSession;
    private Konto konto;

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    
    public void setUzytkownikSession(UzytkownikSession uzytkownikSession) {
        this.uzytkownikSession = uzytkownikSession;
    }
    
    /**
     * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
     * konto przez konto użytkownika którego chcemy wyświetlić
     */
    @PostConstruct
    public void initModel() {
        konto = uzytkownikSession.getKontoUzytkownika();
    }
    /***
     * Metoda wywołująca metode zablokujKonto w uzytkownikSession i wywołująca metodę initModel
     */
    
    public void zablokuj(){
        uzytkownikSession.zablokujKonto();
        initModel();
    }
    /***
     * Funkcja wywołująca funkcje pobierzPoziomy z uzytkownikSession
     * @return 
     */
    public String pobierzPoziomy(){
        return uzytkownikSession.pobierzPoziomy();
    }
    
    /***
     * Metoda wywołująca metode odblokujKonto w uzytkownikSession i wywołująca metodę initModel
     */
    public void odblokuj(){
        uzytkownikSession.odblokujKonto();
        initModel();
    }
    /***
     * Metoda wywołująca metode potwierdzKonto w uzytkownikSession i wywołująca metodę initModel
     */
    public void potwierdz(){
        uzytkownikSession.potwierdzKonto();
        initModel();

    }
    
}
