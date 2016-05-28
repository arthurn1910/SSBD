/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

import javax.ejb.Local;
import java.util.List;

/**
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

    /***
     * Zwraca ogłoszenie o podanym ID
     *
     * @param ID
     * @return
     */
    Ogloszenie znajdzPoID(Long ID);

    int count();

    void flush();

    void refresh(Ogloszenie ogloszenie);
}
