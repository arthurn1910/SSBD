/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

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
public class AgentBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    private Ogloszenie ogloszenie;
    
    
    private DataModel<Konto> agenci;

    /**
     * Metoda inizjalizująca dostępne poziomy dostępu i konta, dla którego
     * będą modyfikowane poziomy dostępu
     */
    @PostConstruct
    private void initModel(){
        agenci = new ListDataModel<Konto>(ogloszenieSession.getAgenci());
        ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
    }
    
    /**
     * Handler dla przycisku dołącz. Metoda dołącza poziom dostępu do konta 
     * @return 
     */
    public String dodajAgenta(){
        ogloszenieSession.przydzielAgentaDoOgloszenia(ogloszenie, agenci.getRowData());
        initModel();
        return "wyswietlSzczegolyOgloszenia";
    }
    
    /**
     * Handler dla przycisku odłącz. Metoda zmieniająca agenta w ogłoszeniu 
     * @return 
     */
    public String zmienAgenta(){
        ogloszenieSession.zmienAgentaWOgloszeniu(ogloszenie, agenci.getRowData());
        initModel();
        return "wyswietlSzczegolyOgloszenia";
    }
    
    /**
     * Metoda dla widoku definiująca czy konto posiada dany poziom dostępu. 
     * Zmienia dostępne klawisze między dołącz i odłącz
     * @return  decyzcja czy konto posiada aktywny poziom dostępu
     */
    public boolean czyPosiadaAgenta(){
        return ogloszenieSession.czyPosiadaAgenta(ogloszenie, agenci.getRowData());
    }
    
    // Gettery i Settery    
    
    public DataModel<Konto> getAgenci() {
        return agenci;
    }    
}
