package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Klasa użytkowa. Umożliwia generowania skrótów MD5 dla haseł
 */
public class MD5Generator {
    
    /**
     * Metoda generująca skrót MD5
     * @param haslo string z wartością do wygenerowania skrótu
     * @return wygenerowany skrót
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public static String generateMD5Hash(String haslo) throws WyjatekSystemu{
        byte[] bajtyWiadomosci = null;
        try {
            bajtyWiadomosci = haslo.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new WyjatekSystemu("blad.nieobslugiwaneKodowanie", ex.getCause());
        }
        MessageDigest generatorMD = null;
        try {
            generatorMD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new WyjatekSystemu("blad.brakAlgorytmuKodowania",ex.getCause());
        }
            
        byte[] zaszyfrowaneBajty = generatorMD.digest(bajtyWiadomosci);
        BigInteger wartoscLiczbowa = new BigInteger(1,zaszyfrowaneBajty);
        return wartoscLiczbowa.toString(16);    
    }
}