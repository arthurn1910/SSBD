/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 * Klasa ta jest wykorzystywana do wyświetlania informacji o wybranym uzytkowniku oraz zablokowania, odblokowania i potwierdzenia jego konta
 * @author rpawlaczyk
 */
@Named
@RequestScoped
public class WyswietlSzczegolyKontaBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;
    
    /***
     * Funkcja pobierająca konto
     * @return 
     */
    public Konto getKonto() {
        return konto;
    }
    
    /**
    * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
    * konto przez konto użytkownika którego chcemy wyświetlić
    */
    @PostConstruct
    public void initModel() {
        konto = uzytkownikSession.getWybraneKonto();
    }
    
    /***
     * Metoda tworząca string złożony z nazw poziomów dostępu danego użytkownika
     * @return 
     */
    public String pobierzPoziomy(){
        String poziomy = "";
        
        for (PoziomDostepu poziom:konto.getPoziomDostepuCollection()) {
            poziomy += poziom.getPoziom() + ", ";
        }
        
        return poziomy;
    }
    
    /***
     * Metoda wywołująca metode zablokujKonto w uzytkownikSession i wywołująca metodę initModel
     */
    
    public void zablokuj(){
        uzytkownikSession.zablokujKonto(konto);
        initModel();
    }
    
    /***
     * Metoda wywołująca metode odblokujKonto w uzytkownikSession i wywołująca metodę initModel
     */
    public void odblokuj(){
        uzytkownikSession.odblokujKonto(konto);
        initModel();
    }
    
    /***
     * Metoda wywołująca metode potwierdzKonto w uzytkownikSession i wywołująca metodę initModel
     */
    public void potwierdz(){
        uzytkownikSession.potwierdzKonto(konto);
        initModel();
    }
    
    public String modyfikujPoziomyDostepu() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("konto", konto);
        return "modyfikujPoziomyDostepu";
    }
    
    public String edytujKonto() {
        uzytkownikSession.pobierzKontoDoEdycji(konto);
        return "edytujDane";
    }
}
