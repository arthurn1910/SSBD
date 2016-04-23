/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

import javax.ejb.Local;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Patryk
 */
@Local
public interface MOKEndpointLocal {
    void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu);

    List<Konto> pobierzWszystkieKonta();

    void potwierdzKonto(Konto konto);

    void odblokujKonto(Konto rowData);

    void zablokujKonto(Konto rowData);

    void zmienHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Konto pobierzKontoDoEdycji(Konto konto) throws IOException, ClassNotFoundException;

    Konto pobierzMojeKonto();
}
