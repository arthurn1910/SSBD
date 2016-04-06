/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.DanePersonalne;

/**
 *
 * @author java
 */
@Local
public interface DanePersonalneFacadeLocal {

    void create(DanePersonalne danePersonalne);

    void edit(DanePersonalne danePersonalne);

    void remove(DanePersonalne danePersonalne);

    DanePersonalne find(Object id);

    List<DanePersonalne> findAll();

    List<DanePersonalne> findRange(int[] range);

    int count();
    
}
