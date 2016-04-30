package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 * Obiekty tej klasy są wykorzystywane do wyszukiwania kont użytkowników
 * @author Maksymilian Zgierski
 */
@Named
@RequestScoped
public class WyszukajKontaBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private DataModel<Konto> kontaDataModel;
    
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private boolean aktywne;
    private boolean potwierdzone;

    public DataModel<Konto> getKontaDataModel() {
        return uzytkownikSession.getKontaDataModel();
    }
            
    public String getImie() {
        return imie;
    }  
    public void setImie(String imie) {
        this.imie = imie;
    }
    
    public String getNazwisko() {
        return nazwisko;
    }    
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefon() {
        return telefon;
    }    
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public boolean isAktywne() {
        return aktywne;
    }
    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public boolean isPotwierdzone() {
        return potwierdzone;
    }
    public void setPotwierdzone(boolean potwierdzone) {
        this.potwierdzone = potwierdzone;
    }
    
    
    
    /**
     * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole kontaWyszukiwanie wszystkimi kontami
     */
    @PostConstruct
    public void init() {
        setAktywne(true);
        setPotwierdzone(true);
    }
  
    /**
     * Inicjalizuje pole kontaWyszukiwanie wszystkimi kontami, których dane pasują do wzorców
     * wpisanych w odpowiednie pola w formularzu
     */
    public void pobierzPodobneKonta() {
        Konto konto = new Konto();
        if (imie != "")
            konto.setImie(imie);
        if (nazwisko != "")
            konto.setNazwisko(nazwisko);
        if (email != "")
            konto.setEmail(email);
        if (telefon != "")
            konto.setTelefon(telefon);   
        konto.setAktywne(aktywne);
        konto.setPotwierdzone(potwierdzone);
        
        uzytkownikSession.setKontaDataModel(new ListDataModel<>(uzytkownikSession.pobierzPodobneKonta(konto)));
    }
    
    public String wyswietlSzczegolyKonta() {    
        uzytkownikSession.setWybraneKonto(getKontaDataModel().getRowData());
        return "wyswietlSzczegolyKonta";
    }
}
