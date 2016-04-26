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
    
    /**
 * Rejestruje konto, nadając mu poziom dostępu klienta
 * @param  k  konto, które ma zostać zarejestrowane
 */
    public void rejestrujKlienta(Konto k) {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());

        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom("KLIENT");
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja,poziomDostepu);
    }
    
    /**
 * Rejestruje konto, nadając mu jeden z poziomów dostępu (klient, agent, menadzer, administrator)
 * @param  k  konto, które ma zostać zarejestrowane
 * @param  poziom  poziom dostępu, który ma mieć nowo tworzone konto
 */
    public void utworzKonto(Konto k, String poziom)
    {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());

        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom(poziom.toUpperCase());
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja,poziomDostepu);
    }
    
  /**
 * Pobiera z endpointa listę kont, których dane pasują do wzorców zawartych w obiekcie Konto, przekazywanym jako parametr
 * @param  k  konto, które zawiera wzorce
 * @return lista kont spełniających wymagania dotyczące wzorców
 */
    List<Konto> pobierzPodobneKonta(Konto k) {
        List<Konto> konta = MOKEndpoint.pobierzPodobneKonta(k);
        return konta;
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
