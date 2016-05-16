package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.IOException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
public class SpotkanieSession implements Serializable {
    @Inject
    private MOSEndpointLocal mosEndpoint;
    
    /**
     * Pobiera listę spotkań dla konta, MOS. 3, Kamil Rogowski
     * MOS.4, MOS.2 P. Stepien
     * @param spotkaniaDlaKonta konto,
     * @return lista spotkań
     */
    public List<Spotkanie> pobierzSpotkania(Konto spotkaniaDlaKonta) {
        return mosEndpoint.pobierzSpotkania(spotkaniaDlaKonta);
    }
      
    /**
     * Pobiera listę spotkań dla konta, 
     * @return lista spotkań
     */
    List<Spotkanie> pobierzSwojeSpotkania() {
        Konto konto = new Konto();
        return mosEndpoint.pobierzSpotkania(konto);
    }


    /**
     * Anuluje spotkania dla konta, MOS.3, Kamil Rogowski
     *
     * @param spotkanieDoAnulowania spotkanie do anulowania
     */
    public void anulujSpotkanie(Spotkanie spotkanieDoAnulowania) {
        mosEndpoint.anulujSpotkanie(spotkanieDoAnulowania);
    }

    /**
     * Pobiera spotkania dla ogłoszenia, MOS. 5, Kamil Rogowski
     *
     * @param ogloszenie ogłoszenie
     * @return lista spotkań
     */
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {

        return mosEndpoint.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    /**
     * rezerwuje spotkanie MOS 1, P. Stepien
     * @param spotkanie 
     */
    public void rezerwujSpotkanie(Spotkanie spotkanie) {
        mosEndpoint.rezerwujSpotkanie(spotkanie);
    }
    
    /**
     * pobiera spotkanie do edycji MOS.2 P. Stepien
     * @return Spotkanie
     */
    public Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) throws WyjatekSystemu, IOException, ClassNotFoundException {
        return mosEndpoint.pobierzSpotkanieDoEdycji(spotkanie);
    }
    
    /**
     * zapisuje spotkanie po edycji MOS.2 P. Stepien
     * @param spotkanie 
     */
    public void zapiszSpotkaniePoEdycji(Spotkanie spotkanie) {

        mosEndpoint.zapiszSpotkaniePoEdycji(spotkanie);
    }
}
