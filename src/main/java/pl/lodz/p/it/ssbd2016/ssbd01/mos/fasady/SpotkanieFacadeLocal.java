/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

import javax.ejb.Local;
import java.util.List;

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
    
    /**
     * metoda zwraca liste spotkan danego uzytkownika/agenta
     * @param konto
     * @return 
     */
    List<Spotkanie> pobierzSpotkaniaUzytkownika(Konto konto);

    /**
     * metoda zwraca listę spotkań dla ogłoszenia
     * @param ogloszenie ogłoszenie
     * @return lista spotkań
     */
    List<Spotkanie> findByOgloszenie(Ogloszenie ogloszenie);

}
