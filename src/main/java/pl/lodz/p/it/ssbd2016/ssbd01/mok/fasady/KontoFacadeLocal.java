/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author java
 */
@Local
public interface KontoFacadeLocal {

    void create(Konto konto);

    void edit(Konto konto);

    void remove(Konto konto);

    void flush();

    Konto find(Object id);

    List<Konto> findAll();

    List<Konto> findRange(int[] range);

    int count();

    public Konto znajdzPoLoginie(String login);
    
    public List<Konto> pobierzWszystkieNiepotwierdzoneKonta();

    public List<Konto> znajdzPodobne(Konto konto);
}