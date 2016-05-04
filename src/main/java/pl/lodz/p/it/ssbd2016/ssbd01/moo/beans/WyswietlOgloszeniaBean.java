/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author java
 */
@Named
@RequestScoped
public class WyswietlOgloszeniaBean {

    public WyswietlOgloszeniaBean() {
    }
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private List<Ogloszenie> ogloszenia;
    private DataModel<Ogloszenie> ogloszeniaDataModel;

    public DataModel<Ogloszenie> getOgloszeniaDataModel() {
        return ogloszeniaDataModel;
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
    
    public void deaktywujOgloszenie() {
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
