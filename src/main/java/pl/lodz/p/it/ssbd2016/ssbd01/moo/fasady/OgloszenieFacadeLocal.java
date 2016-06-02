/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 *
 * @author java
 */
@Local
public interface OgloszenieFacadeLocal {

    void create(Ogloszenie ogloszenie);

    void edit(Ogloszenie ogloszenie);

    void remove(Ogloszenie ogloszenie);

    Ogloszenie find(Object id);

    List<Ogloszenie> findAll();

    List<Ogloszenie> findRange(int[] range);

    int count();    
    Ogloszenie znajdzPoID(Long ID);
}
