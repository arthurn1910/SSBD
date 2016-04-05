/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ulubione;

/**
 *
 * @author java
 */
@Local
public interface UlubioneFacadeLocal {

    void create(Ulubione ulubione);

    void edit(Ulubione ulubione);

    void remove(Ulubione ulubione);

    Ulubione find(Object id);

    List<Ulubione> findAll();

    List<Ulubione> findRange(int[] range);

    int count();
    
}
