
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

@Local
public interface MOKEndpointLocal {
    Konto pobierzPierwszeKonto();
    public List<Konto> pobierzKonta();
}
