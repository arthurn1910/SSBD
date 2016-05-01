package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession;

/**
 * Klasa użytkowa. Umożliwia generowania skrótów MD5 dla haseł
 */
public class MD5Generator {
    
    /**
     * Metoda generująca skrót MD5
     * @param haslo string z wartością do wygenerowania skrótu
     * @return wygenerowany skrót
     */
    public static String generateMD5Hash(String haslo) {
        byte[] bajtyWiadomosci = null;
        try {
            bajtyWiadomosci = haslo.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageDigest generatorMD = null;
        try {
            generatorMD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        byte[] zaszyfrowaneBajty = generatorMD.digest(bajtyWiadomosci);
        BigInteger wartoscLiczbowa = new BigInteger(1,zaszyfrowaneBajty);
        return wartoscLiczbowa.toString(16);    
    }
}