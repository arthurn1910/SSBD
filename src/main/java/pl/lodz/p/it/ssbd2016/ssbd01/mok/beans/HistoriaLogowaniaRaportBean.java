package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Bean odpowiedzialny za wygenerowanie raportu dla admina
 *
 * @author Kamil Rogowski
 */
@RequestScoped
@Named
public class HistoriaLogowaniaRaportBean {

    @Inject
    private UzytkownikSession uzytkownikSession;
    /**
     * Lista wszystkich historii logowania
     */
    private DataModel<HistoriaLogowania> historiaLogowanKont;


    /**
     * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
     * historiaLogowanKont wszystkimi kontami
     */
    @PostConstruct
    public void init() {
        List<HistoriaLogowania> historiaLogowan = uzytkownikSession.pobierzHistorieLogowanUzytkownikow();
        historiaLogowanKont = new ListDataModel(historiaLogowan);
    }

    public DataModel<HistoriaLogowania> getHistoriaLogowanKont() {
        return historiaLogowanKont;
    }

    public UzytkownikSession getUzytkownikSession() {
        return uzytkownikSession;
    }

}
