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
    
    /*
    @param ogloszenie innego użytkownika, które ma zostać deaktywowane
    Przypadek użycia - MOO5
    */
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) {
        ogloszenieSession.deaktywujOgloszenieInnegoUzytkownika(ogloszenie);
    }
    
    

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
    
    public void dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszeniaDataModel.getRowData());
    }
}
