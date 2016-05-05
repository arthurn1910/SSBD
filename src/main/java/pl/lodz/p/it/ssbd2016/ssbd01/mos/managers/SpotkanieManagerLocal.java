package pl.lodz.p.it.ssbd2016.ssbd01.mos.managers;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla menadżera spotkań
 *
 * @author Kamil Rogowski
 */
@Local
public interface SpotkanieManagerLocal {

    /**
     * Pobiera listę spotkań dla ogłosznia MOS.5, Kamil Rogowski
     *
     * @param ogloszenie ogłoszenie dla którego szukamy spotkań
     * @return lista spotkań
     */
    List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie);

    /**
     * Pobiera spotkania powiązane z kontem,
     * zachowuje, zmienilem nazwe MOS.4, P. Stepien
     * @param konto konto dla którego szukamy spotkań
     * @return ista spotkań
     */
    List<Spotkanie> pobierzUmowioneSpotkania(Konto konto);

}