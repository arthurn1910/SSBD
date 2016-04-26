package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import javax.faces.model.ListDataModel;

/**
 * Obiekty tej klasy są wykorzystywane do wyświetlania kont użytkowników niepotwierdzonych
 * @author Maksymilian Zgierski
 */
@Named
@RequestScoped
public class WyswietlKontaNiepotwierdzone implements Serializable {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private DataModel<Konto> kontaNiepotwierdzone;

    public DataModel<Konto> getKontaNiepotwierdzone() {
        return kontaNiepotwierdzone;
    }
    
  /**
 * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
 * kontaNiepotwierdzone wszystkimi niepotwierdzonymi kontami
 */
    @PostConstruct
    public void init() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        List<Konto> konta = new ArrayList();
        for(int i = 0; i < temp.size(); i++)
        {
            if(temp.get(i).getPotwierdzone() == false)
                konta.add(temp.get(i));
        }
        kontaNiepotwierdzone = new ListDataModel(konta);
    }
    
  /**
 * Potwierdza kont, które zostało wybrane w formularzu (poprzez naciśnięcie przycisku obok konta)
 */
    public void potwierdzKonto() {
        uzytkownikSession.potwierdzKonto(kontaNiepotwierdzone.getRowData());
        init();
    }
}
