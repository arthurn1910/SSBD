/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author java
 */
@Local
public interface KontoManagerLocal {
    public List<Konto> znajdzPodobne(Konto konto);

    public boolean dodajPoziomDostepu(Konto konto, String poziom);

    public boolean odlaczPoziomDostepu(Konto konto, String poziom);
}
