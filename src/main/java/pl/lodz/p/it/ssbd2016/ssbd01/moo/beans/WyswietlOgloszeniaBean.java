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
import java.util.Collections;
import java.util.Comparator;


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
    
    /**
    Stworzył: Maksymilian Zgierski
    Przypadek użycia: MOO.9 - Przeglądaj ogłoszenia posortowane
    
    Poniższe pola będą użyte w formularzu JSF i będą określały według jakich kryteriów przeprowadzić
    sortowanie w metodzie sortujOgloszenia
    
    Przewidywane kontrolki w JSF: selectBooleanCheckBoxes dla sortujCena, sortujDataDodania, 
    sortujTypOgloszenia, sortujRynekPierwotny
    selectOneRadio dla pozostałych
    */
    private boolean sortujCena;
    private boolean sortujDataDodania;
    private boolean sortujTypOgloszenia;
    private boolean sortujRynekPierwotny;
    
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
    
    /**
     * Wyświetla ogłoszenia posortowane
     */
    public void sortujOgloszenia() {
        ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        Collections.sort(ogloszenia, new Comparator() {
            public int compare(Object o1, Object o2) {
                Ogloszenie x1 = (Ogloszenie) o1;
                Ogloszenie x2 = (Ogloszenie) o2;
                if(sortujCena == true) {
                    int sComp1 = 0;
                    if(x1.getCena() > x2.getCena())
                        sComp1 = 1;
                    else if(x1.getCena() < x2.getCena())
                        sComp1 = -1;
                    if (sComp1 != 0)
                        return sComp1;
                    else {
                        if(sortujDataDodania == true) {
                            int sComp2 = 0;
                            if(x1.getDataDodania().after(x2.getDataDodania()))
                                sComp2 = 1;
                            else if(x1.getDataDodania().before(x2.getDataDodania()))
                                sComp2 = -1;
                            if (sComp2 != 0)
                                return sComp2;
                            else {
                                if(sortujTypOgloszenia == true) {
                                    int sComp3 = x1.getTypOgloszenia().getNazwa().compareTo(x2.getTypOgloszenia().getNazwa());
                                    if (sComp3 != 0)
                                        return sComp3;
                                    else {
                                        if(sortujRynekPierwotny == true) {
                                           int sComp4 = 0;
                                           if(x1.getRynekPierwotny() == true && x2.getRynekPierwotny() == false)
                                               sComp4 = -1;
                                           else if(x1.getRynekPierwotny() == false && x2.getRynekPierwotny() == true)
                                               sComp4 = 1;
                                           return sComp4;
                                        }
                                    }
                                }
                            }
                        }            
                    }
                }
                else if(sortujDataDodania == true) {
                    int sComp2 = 0;
                    if(x1.getDataDodania().after(x2.getDataDodania()))
                        sComp2 = 1;
                    else if(x1.getDataDodania().before(x2.getDataDodania()))
                        sComp2 = -1;
                    if (sComp2 != 0)
                        return sComp2;
                    else {
                        if(sortujTypOgloszenia == true) {
                            int sComp3 = x1.getTypOgloszenia().getNazwa().compareTo(x2.getTypOgloszenia().getNazwa());
                            if (sComp3 != 0)
                                return sComp3;
                            else {
                                if(sortujRynekPierwotny == true) {
                                    int sComp4 = 0;
                                    if(x1.getRynekPierwotny() == true && x2.getRynekPierwotny() == false)
                                        sComp4 = -1;
                                    else if(x1.getRynekPierwotny() == false && x2.getRynekPierwotny() == true)
                                        sComp4 = 1;
                                    return sComp4;
                                }
                            }
                        }
                    }
                }
                else if(sortujTypOgloszenia == true) {
                    int sComp3 = x1.getTypOgloszenia().getNazwa().compareTo(x2.getTypOgloszenia().getNazwa());
                    if (sComp3 != 0)
                        return sComp3;
                    else {
                        if(sortujRynekPierwotny == true) {
                            int sComp4 = 0;
                            if(x1.getRynekPierwotny() == true && x2.getRynekPierwotny() == false)
                                sComp4 = -1;
                            else if(x1.getRynekPierwotny() == false && x2.getRynekPierwotny() == true)
                                sComp4 = 1;
                            return sComp4;
                        }
                    }
                }
                else if(sortujRynekPierwotny == true) {
                    int sComp4 = 0;
                    if(x1.getRynekPierwotny() == true && x2.getRynekPierwotny() == false)
                        sComp4 = -1;
                    else if(x1.getRynekPierwotny() == false && x2.getRynekPierwotny() == true)
                        sComp4 = 1;
                    return sComp4;
                }
                return 1;
            }
        });
        ogloszeniaDataModel = new ListDataModel<Ogloszenie>(ogloszenia);
    }
    
    /***
     * Funckja ustawiająca w ogloszenieSession ogłoszenie do wysiwetlenia
     * @return 
     */
    public String wyswietlSzczegolyOgloszenia(){
        ogloszenieSession.setOgloszenieDoWyswietlenia(getOgloszeniaDataModel().getRowData());
        return "wyswietlSzczegolyOgloszenia";
    }
    
}