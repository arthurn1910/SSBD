package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 * 
 */
@Named
@RequestScoped
public class WyswietlOgloszeniaBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private List<Ogloszenie> ogloszenia;
    private DataModel<Ogloszenie> ogloszeniaDataModel;
    
    /*
    @param ogloszenie innego użytkownika, które ma zostać deaktywowane
    Przypadek użycia - MOO5
    */
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) {
        ogloszenieSession.deaktywujOgloszenieInnegoUzytkownika(ogloszenie);
    }
    
    // w tej metodzie będzie się odbywać wyswietlenie ogloszen nieposortowanych
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
    
    // w tej metodzie będzie się odbywać sortowanie według wybranych kryteriów
    public void sortujOgloszenia() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        /*
        ...
        */
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
    
    /*
    Stworzył: Maksymilian Zgierski
    Przypadek użycia: MOO.4 - Deaktywuj ogłoszenie dotyczące danego użytkownika 
    @param ogloszenie, które ma zostać deaktywowane
    */
    public void deaktywujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenie) {
        ogloszenieSession.deaktywujOgloszenieDanegoUzytkownika(ogloszenie);
    }
    
    
    @PostConstruct
    private void initModel() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
    
    public void aktywujOgloszenie() {
        ogloszenieSession.aktywujOgloszenie(ogloszeniaDataModel.getRowData());
        initModel();
    }
    
    public void deaktywujOgloszenie() throws Exception {
        ogloszenieSession.deaktywujOgloszenie(ogloszeniaDataModel.getRowData());
        initModel();
    }
    /***
     * Metoda wywołuje metodę zmienAgentaWOgloszeniu w OgloszenieSession przekazując jej parametry Ogloszenie, Konto
     * Stowrzył Radosław Pawlaczyk
     * MOO 7
     */
    public void zmienAgentaWOgloszeniu(){
        Konto k=new Konto();
        ogloszenieSession.zmienAgentaWOgloszeniu(ogloszeniaDataModel.getRowData(), k);
    }
    
    /***
     * Metoda wywołuje metodę przydzielAgentaDoOgloszenia w OgloszenieSession przekazując jej parametry Ogloszenie, Konto
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     */
    void przydzielAgentaDoOgloszenia(){
        Konto agent=new Konto();
        ogloszenieSession.przydzielAgentaDoOgloszenia(ogloszeniaDataModel.getRowData(), agent);
    }
    
    public void dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszeniaDataModel.getRowData());
    }
}
