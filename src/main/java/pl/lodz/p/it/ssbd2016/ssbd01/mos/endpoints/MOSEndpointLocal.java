
package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

@Local
public interface MOSEndpointLocal {
    void dodajSpotkanie(Spotkanie spotkanie);
    Ogloszenie pobierzPierwszeOgloszenie();
    Konto pobierzPierwszeKonto();
}
