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
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.OgloszenieDeaktywowaneWczesniej;

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
    
    public String deaktywujOgloszenie() {
        try{
            ogloszenieSession.deaktywujOgloszenie(ogloszeniaDataModel.getRowData());
        } catch(OgloszenieDeaktywowaneWczesniej ex){
            return "OgloszenieDeaktywowaneWczesniej";
        }
        initModel();
        return "";
    }
    
    public void dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszeniaDataModel.getRowData());
    }
}
