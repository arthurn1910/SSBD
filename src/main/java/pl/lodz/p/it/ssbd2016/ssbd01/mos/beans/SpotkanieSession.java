package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SessionScoped
public class SpotkanieSession implements Serializable {
    @Inject
    private MOSEndpointLocal mosEndpoint;
    
    public void dodajSpotkanie(Konto k, Ogloszenie o, String rok, String miesiac, String dzien, String dlugosc) {
        Konto konto = mosEndpoint.pobierzPierwszeKonto();
        
        Ogloszenie ogloszenie = mosEndpoint.pobierzPierwszeOgloszenie();
        Spotkanie spotkanie = new Spotkanie();
        spotkanie.setDataSpotkania(new Date(Integer.parseInt(rok) - 1900, Integer.parseInt(miesiac), Integer.parseInt(dzien)));
        spotkanie.setDlugoscSpotkania(Integer.parseInt(dlugosc));
        spotkanie.setIdUzytkownika(konto);
        spotkanie.setIdOgloszenia(ogloszenie);
        mosEndpoint.dodajSpotkanie(spotkanie);
        
        konto.getSpotkanieCollection().add(spotkanie);
        ogloszenie.getSpotkanieCollection().add(spotkanie);        
    }
    
    public Konto pobierzPierwszeKonto() {
        Konto konto = mosEndpoint.pobierzPierwszeKonto();
        return konto;
    }

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
     * Anuluje spotkania dla konta, MOS.3, Kamil Rogowski
     *
     * @param konto                 konto ze spotkaniami
     * @param spotkanieDoAnulowania spotkanie do anulowania
     */
    public void anulujSpotkanie(Konto konto, Spotkanie spotkanieDoAnulowania) {
        mosEndpoint.anulujSpotkanie(konto, spotkanieDoAnulowania);
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
     * @param spotkanie
     * @return 
     */
    public Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) {

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
