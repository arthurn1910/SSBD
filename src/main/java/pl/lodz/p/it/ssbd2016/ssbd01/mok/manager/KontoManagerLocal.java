package pl.lodz.p.it.ssbd2016.ssbd01.mok.manager;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.ejb.Local;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 */
@Local
public interface KontoManagerLocal {

    void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    void zmienHaslo(Konto konto, String noweHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
