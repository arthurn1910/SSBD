
package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

@Local
public interface MOOEndpointLocal {
    Ogloszenie pobierzPierwszeOgloszenie();
}
