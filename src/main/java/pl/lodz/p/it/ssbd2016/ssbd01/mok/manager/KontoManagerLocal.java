package pl.lodz.p.it.ssbd2016.ssbd01.mok.manager;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.ejb.Local;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 *  @author Kamil Rogowski
 */
@Local
public interface KontoManagerLocal {
    /**
     * Przypadek gdy modyfikujemy własne hasło
     *
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasłow w postaci jawnej
     */
    void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHaslo);

    /**
     * Przypadek gdy admin modyfikuje nam hasło
     * @param konto
     * @param noweHaslo
     */
    void zmienHaslo(Konto konto, String noweHaslo);
}
