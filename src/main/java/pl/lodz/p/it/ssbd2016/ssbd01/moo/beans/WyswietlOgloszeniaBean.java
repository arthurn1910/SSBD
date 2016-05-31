package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
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
    
 
    /**
     * Inijcalizuje dane
     */
    @PostConstruct
    private void initModel() {
        List<Ogloszenie> ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        ogloszenieSession.setOgloszeniaDataModel(new ListDataModel<Ogloszenie>(ogloszenia));
    }
        
    /**
     * Wyświetla ogłoszenia nieposortowane
     */
    public void wyswietlOgloszeniaNieposortowane() {
        List<Ogloszenie> ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        /*
        ... ewentualnie kiedy zostanie zaimplementowane MOO9 to można wykorzystać jeden domyślny tryb sortowania dla MOO8
        */
        ogloszenieSession.setOgloszeniaDataModel(new ListDataModel<Ogloszenie>(ogloszenia));
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
    private String sortuj;
    
    public String getSortuj() {
        return this.sortuj;
    }    
    public void setSortuj(String s) {
        this.sortuj = s;
    }
    
    /**
     * Wyświetla ogłoszenia posortowane
     */
    public void sortujOgloszenia() {
        List<Ogloszenie> ogloszenia = ogloszenieSession.pobierzWszystkieOgloszenia();
        Collections.sort(ogloszenia, new Comparator() {
            public int compare(Object o1, Object o2) {
                Ogloszenie x1 = (Ogloszenie) o1;
                Ogloszenie x2 = (Ogloszenie) o2;
                if(sortuj.equals("cena") == true) {
                    int sComp = 0;
                    if(x1.getCena() > x2.getCena())
                        sComp = 1;
                    else if(x1.getCena() < x2.getCena())
                        sComp = -1;
                    return sComp;
                }
                else if(sortuj.equals("dataDodania") == true) {
                    int sComp = 0;
                    if(x1.getDataDodania().after(x2.getDataDodania()))
                        sComp = 1;
                    else if(x1.getDataDodania().before(x2.getDataDodania()))
                        sComp = -1;
                    return sComp;
                }
                else if(sortuj.equals("typOgloszenia") == true) {
                    int sComp = x1.getTypOgloszenia().getNazwa().compareTo(x2.getTypOgloszenia().getNazwa());
                    return sComp;
                }
                else if(sortuj.equals("rynekPierwotny") == true) {
                    int sComp = 0;
                    if(x1.getRynekPierwotny() == true && x2.getRynekPierwotny() == false)
                        sComp = -1;
                    else if(x1.getRynekPierwotny() == false && x2.getRynekPierwotny() == true)
                        sComp = 1;
                    return sComp;
                }
                return 1;
            }
        });
        ogloszenieSession.setOgloszeniaDataModel(new ListDataModel<>(ogloszenia));
    }
    
    /***
     * Funckja ustawiająca w ogloszenieSession ogłoszenie do wysiwetlenia
     * @return 
     */
    public String wyswietlSzczegolyOgloszenia() {
        ogloszenieSession.setOgloszenieDoWyswietlenia(getOgloszeniaDataModel().getRowData());
        return "wyswietlSzczegolyOgloszenia";
    }
    
    public DataModel<Ogloszenie> getOgloszeniaDataModel() {
        return ogloszenieSession.getOgloszeniaDataModel();
    }
    
}