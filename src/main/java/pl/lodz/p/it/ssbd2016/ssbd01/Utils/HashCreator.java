package pl.lodz.p.it.ssbd2016.ssbd01.Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 * Klasa użytkowa kowertująca string na hash MD5
 */
public class HashCreator {

    private HashCreator() {

    }

    /**
     * konwertuje bajty na stringi w hex
     *
     * @param data bajty danych
     * @return hex
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * Przyjmuje string zwraca skrót w postaci MD5
     *
     * @param text tekst do zmiany
     * @return skrót w postaci MD5
     */
    public static String MD5(String text) {
        MessageDigest md;
        byte[] md5hash = new byte[32];
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            md5hash = md.digest();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Logger.getLogger(HashCreator.class.getName()).log(Level.SEVERE, null, e);
        }
        return convertToHex(md5hash);
    }
}
