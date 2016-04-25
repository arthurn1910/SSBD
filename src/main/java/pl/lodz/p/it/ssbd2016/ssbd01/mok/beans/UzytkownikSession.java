/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;
import javax.ejb.EJB;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
/*
 *
 * @author Patryk
 */
@SessionScoped
public class UzytkownikSession implements Serializable {
    @EJB
    private MOKEndpointLocal MOKEndpoint;
    
    public void rejestrujKlienta(Konto k) {
        
        
        //String cryptedPass = Arrays.toString(cryptedBytes);
        
        
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("blablabla"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie("Janusz");
        kontoRejestracja.setNazwisko("Andrzej");
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());
       
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja);
    }
    
    List<Konto> pobierzWszystkieKonta() {
        return MOKEndpoint.pobierzWszystkieKonta();
    }
    
    void potwierdzKonto(Konto rowData) {
        MOKEndpoint.potwierdzKonto(rowData);
    }
    
      void odblokujKonto(Konto rowData) {
        MOKEndpoint.odblokujKonto(rowData);
    }

    void zablokujKonto(Konto rowData) {
        MOKEndpoint.zablokujKonto(rowData);
    }
}
