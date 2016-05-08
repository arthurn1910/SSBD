package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;


/**
 * Obsługuje widok wyświetlania ogłoszeń
 */
@Named
@RequestScoped
public class WyswietlOgloszeniaBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private List<Ogloszenie> ogloszenia;
    private DataModel<Ogloszenie> ogloszeniaDataModel;
    
    
    /**
     * Inijcalizuje dane
     */
    @PostConstruct
    private void initModel() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
        
    /**
     * Wyświetla ogłoszenia nieposortowane
     */
    public void wyswietlOgloszeniaNieposortowane() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        /*
        ... ewentualnie kiedy zostanie zaimplementowane MOO9 to można wykorzystać jeden domyślny tryb sortowania dla MOO8
        */
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
    
    

    public DataModel<Ogloszenie> getOgloszeniaDataModel() {
        return ogloszeniaDataModel;
    }
    
    /*
    Stworzył: Maksymilian Zgierski
    Przypadek użycia: MOO.9 - Przeglądaj ogłoszenia posortowane
    
    Poniższe pola będą użyte w formularzu JSF i będą określały według jakich kryteriów przeprowadzić
    sortowanie w metodzie sortujOgloszenia
    
    Przewidywane kontrolki w JSF: selectBooleanCheckBoxes dla sortujCena, sortujDataDodania, 
    sortujTypOgloszenia, sortujRynekPierwotny
    selectOneRadio dla pozostałych
    */
    private boolean sortujCena;
    private boolean cenaRosnaca;
    private boolean sortujDataDodania;
    private boolean dataRosnaca;
    private boolean sortujTypOgloszenia;
    private boolean typOgloszeniaAlfabetycznie;
    private boolean sortujRynekPierwotny;
    private boolean rynekPierwotnyNajpierw;
    
    public boolean getSortujCena() {
        return this.sortujCena;
    }    
    public void setSortujCena(boolean c) {
        this.sortujCena = c;
    }
    
    public boolean getSortujDataDodania() {
        return this.sortujDataDodania;
    }
    public void setSortujDataDodania(boolean d) {
        this.sortujDataDodania = d;
    }
    
    public boolean getSortujTypOgloszenia() {
        return this.sortujTypOgloszenia;
    }    
    public void setSortujTypOgloszenia(boolean t) {
        this.sortujTypOgloszenia = t;
    }
    
    public boolean getSortujRynekPierwotny() {
        return this.sortujRynekPierwotny;
    }
    public void setSortujRynekPierwotny(boolean r) {
        this.sortujRynekPierwotny = r;
    }
    
    public boolean getCenaRosnaca() {
        return this.cenaRosnaca;
    }
    public void setCenaRosnaca(boolean c) {
        this.cenaRosnaca = c;
    }
    
    public boolean getDataRosnaca() {
        return this.dataRosnaca;
    }
    public void setDataRosnaca(boolean d) {
        this.dataRosnaca = d;
    }
    
    public boolean getTypOgloszeniaAlfabetycznie() {
        return this.typOgloszeniaAlfabetycznie;
    }
    public void setTypOgloszeniaAlfabetycznie(boolean t) {
        this.typOgloszeniaAlfabetycznie = t;
    }
    
    public boolean getRynekPierwotnyNajpierw() {
        return this.rynekPierwotnyNajpierw;
    }
    public void setRynekPierwotnyNajpierw(boolean r) {
        this.rynekPierwotnyNajpierw = r;
    }
    
    /**
     * Wyświetla ogłoszenia posortowane
     */
    public void sortujOgloszenia() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        /*
        ...
        */
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
}
