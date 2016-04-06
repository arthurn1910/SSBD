/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.NieruchomoscPosiada;

/**
 *
 * @author java
 */
@Local
public interface NieruchomoscPosiadaFacadeLocal {

    void create(NieruchomoscPosiada nieruchomoscPosiada);

    void edit(NieruchomoscPosiada nieruchomoscPosiada);

    void remove(NieruchomoscPosiada nieruchomoscPosiada);

    NieruchomoscPosiada find(Object id);

    List<NieruchomoscPosiada> findAll();

    List<NieruchomoscPosiada> findRange(int[] range);

    int count();
    
}
