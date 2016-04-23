package pl.lodz.p.it.ssbd2016.ssbd01.mok.manager;

import javax.ejb.Local;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 */
@Local
public interface KontoManagerLocal {

    void zmienHaslo(String konto, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
