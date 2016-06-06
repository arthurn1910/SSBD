package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

import javax.ejb.Local;
import java.io.IOException;
import java.util.List;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOS
 */
@Local
public interface MOSEndpointLocal {

    /**
     * rezerwacja spotkania MOS 1, Radosław Pawlaczyk
     * @param spotkanie
     */
    void rezerwujSpotkanie(Spotkanie spotkanie, String login) throws WyjatekSystemu;

    /**
     * pobiera spotkanie do edycji MOS 2, P. Stepien
     * @param spotkanie
     * @return
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) throws WyjatekSystemu, IOException, ClassNotFoundException;

    /**
     * zapisuje edytowane spotkanie po edycji MOS 2, P. Stepien
     * @param spotkanie
     */
    void zapiszSpotkaniePoEdycji(Spotkanie spotkanie);

    /**
     * Anuluje spotkanie powiąane z kontem, stworzona dla MOS.3, Kamil Rogowski
     * @param spotkanieDoAnulowania spotkanie do anulowania
     */
    void anulujSpotkanie(Spotkanie spotkanieDoAnulowania);

    /**
     * Pobiera wszystkie spotkania, stworzona dla MOS.4, Kamil Rogowski
     * @param spotkanieDlaKonta
     * @return lista spotkań
     * @author Kamil Rogowski
     */
    List<Spotkanie> pobierzSpotkania(Konto spotkanieDlaKonta);

    /**
     * Pobiera spotkania dla ogłoszenia, MOS.5, Kamil Rogowski
     * @param ogloszenie ogloszenie
     * @return lista spotkan
     */
    List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie);

    /**
     * Pobiera ogloszenie dla ktorego maja byc wyswietlone spotkania
     * @param id ogloszenia
     * @return znalezione ogloszenie
     */
    Ogloszenie znajdzOgloszeniePoId(Long id);

    /**
     * Pobiera konto aktualnie zalogowanego użytkownika
     * @return konto użytkownika
     */
    Konto pobierzMojeKonto();

}
