/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

/**
 *
 * @author java
 */
@Local
public interface SpotkanieFacadeLocal {

    void create(Spotkanie spotkanie);

    void edit(Spotkanie spotkanie);

    void remove(Spotkanie spotkanie);

    Spotkanie find(Object id);

    List<Spotkanie> findAll();

    List<Spotkanie> findRange(int[] range);

    int count();
    
}
