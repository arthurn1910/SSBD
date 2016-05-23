/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author java
 */
@Local
public interface KontoMOOFacadeLocal {

    void create(Konto konto);

    void edit(Konto konto);

    void remove(Konto konto);

    Konto find(Object id);

    List<Konto> findAll();

    List<Konto> findRange(int[] range);

    Konto znajdzPoLoginie(String login);
    
    int count();

    public void flush();
    
}
