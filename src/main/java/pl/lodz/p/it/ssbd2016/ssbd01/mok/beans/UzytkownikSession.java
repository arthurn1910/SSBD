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
import static pl.lodz.p.it.ssbd2016.ssbd01.encje.ssbd01adminPU.PoziomDostepu_.poziom;
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
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja,poziomDostepu);
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

    List<Konto> pobierzWszystkieNiepotwierdzoneKonta() {
        return MOKEndpoint.pobierzWszystkieNiepotwierdzoneKonta();
    }

    Konto pobierzUrzytkownika(String login) {
        return MOKEndpoint.pobierzUzytkownika(login);
    }

    List<Konto> pobierzPodobneKonta(Konto konto) {
        return MOKEndpoint.pobierzPodobneKonta(konto);
    }

    void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.dodajPoziomDostepu(konto, poziom);
    }

    void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
    }
}
