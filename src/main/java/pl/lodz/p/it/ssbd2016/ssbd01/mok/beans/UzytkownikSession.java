/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie("Janusz");
        kontoRejestracja.setNazwisko("Pospolity");
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());

        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom("AGENT");
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        System.out.println(kontoRejestracja.getLogin());
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja,poziomDostepu);
        System.out.println(kontoRejestracja.getLogin());
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
