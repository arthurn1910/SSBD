/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;

/**
 *
 * @author Gleam
 */
@Local
public interface TypNieruchomosciFacadeLocal {

    void create(TypNieruchomosci typNieruchomosci);

    void edit(TypNieruchomosci typNieruchomosci);

    void remove(TypNieruchomosci typNieruchomosci);

    TypNieruchomosci find(Object id);

    List<TypNieruchomosci> findAll();

    List<TypNieruchomosci> findRange(int[] range);

    int count();
    
}
