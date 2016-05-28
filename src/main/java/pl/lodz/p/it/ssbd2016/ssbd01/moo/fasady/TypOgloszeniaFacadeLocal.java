/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;

import javax.ejb.Local;
import java.util.List;

/**
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

    public TypOgloszenia znajdzPoNazwie(String typ);

    void flush();
}
