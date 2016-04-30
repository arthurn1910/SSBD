package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import javax.inject.Named;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 * Obsługa zmiany danych przez admina i użytkownika
 */
@Named
@RequestScoped
public class EdytujDaneBean implements Serializable {

    @Inject
    private UzytkownikSession uzytkownikSession;
    
    /**
     * Metoda zapisuje zmiany po edycji konta
     */
    public void zapiszKontoPoEdycji() {
        uzytkownikSession.zapiszKontoPoEdycji();
    }
    
    public Konto getKontoEdytuj() {
        return uzytkownikSession.getKontoEdytuj();
    }
}