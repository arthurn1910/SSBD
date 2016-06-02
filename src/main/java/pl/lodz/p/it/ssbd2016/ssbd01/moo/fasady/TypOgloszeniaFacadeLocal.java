/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;

/**
 *
 * @author java
 */
@Local
public interface TypOgloszeniaFacadeLocal {

    void create(TypOgloszenia typOgloszenia);

    void edit(TypOgloszenia typOgloszenia);

    void remove(TypOgloszenia typOgloszenia);

    TypOgloszenia find(Object id);

    List<TypOgloszenia> findAll();

    List<TypOgloszenia> findRange(int[] range);

    int count();

    /**
     * Metoda zwraca obiekt TypOgloszenia o nazwie takiej jak podana w parametrze
     * @param typ nazwa typu ogłoszenia
     * @return obiekt TypOgloszenia
     */
    public TypOgloszenia znajdzPoNazwie(String typ);
    
}
