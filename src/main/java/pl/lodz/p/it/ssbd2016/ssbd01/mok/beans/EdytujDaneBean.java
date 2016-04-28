package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 * Obsługa zmiany danych przez admina i użytkownika
 */
@ManagedBean
@RequestScoped
public class EdytujDaneBean implements Serializable {

    @Inject
    private UzytkownikSession uzytkownikSession;
    private Konto konto;

    @PostConstruct
    private void initKonto() {

        konto = uzytkownikSession.znajdzPoLoginie("kontoA");
        uzytkownikSession.pobierzKontoDoEdycji(konto);
    }

    /**
     * Metoda zapisuje zmiany po edycji konta
     */
    public void zapiszPoEdycji() {

        uzytkownikSession.zapiszKontoPoEdycji();
    }

    public Konto getKonto() {
        return konto;
    }

    public Konto getUzytkownikSession() {
        return uzytkownikSession.getKontoEdytuj();
    }

    public void setUzytkownikSession(UzytkownikSession uzytkownikSession) {
        this.uzytkownikSession = uzytkownikSession;
    }


}