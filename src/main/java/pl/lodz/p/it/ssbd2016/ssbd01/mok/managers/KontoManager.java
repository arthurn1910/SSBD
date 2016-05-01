package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.MD5Generator;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 * Klasa pośrednicząca miedzy MOKEndpoint a fasadami. Przetwarza niezbędne dane.
 * Implementuje interfejs KontoManagaerLocal
 */
@Stateless
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class KontoManager implements KontoManagerLocal {

    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;   

    @Resource
    private SessionContext sessionContext;

    @Override
    @RolesAllowed("zmienMojeHaslo")
    public void zmienMojeHaslo(Konto konto, String noweHaslo, String stareHasloWpisane) throws Exception {
        if (!konto.getLogin().equals(sessionContext.getCallerPrincipal().getName())) {
            throw new Exception("Nie moje konto");
        }
        String stareHaslo = konto.getHaslo();
        String hashedPassword = null;

        hashedPassword = MD5Generator.generateMD5Hash(noweHaslo);
        stareHasloWpisane = MD5Generator.generateMD5Hash(stareHasloWpisane);
        if (stareHasloWpisane.equals(stareHaslo)) {
            konto.setHaslo(hashedPassword);
            kontoFacade.edit(konto);
        } else {
            throw new Exception("Hasla sie nie zgadzaja");
        }
    }

    @Override
    @RolesAllowed("zmienHaslo")
    public void zmienHaslo(Konto konto, String noweHaslo) {
        String noweZahashowanehaslo = MD5Generator.generateMD5Hash(noweHaslo);
        konto.setHaslo(noweZahashowanehaslo);
        kontoFacade.edit(konto);
    }
    
    @Override
    @PermitAll
    public void rejestrujKontoKlienta(Konto konto) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
        kontoFacade.create(konto);
        
        PoziomDostepu poziomDostepu = PoziomDostepuManager.stworzPoziomDostepuKlient();
        poziomDostepu.setKontoId(konto);
        
        poziomDostepuFacade.create(poziomDostepu);
        
        konto.getPoziomDostepuCollection().add(poziomDostepu);
    }
    
    @Override
    @RolesAllowed("utworzKonto")
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) throws Exception {
        if (PoziomDostepuManager.czyPoprawnaKombinacjaPoziomowDostepu(poziomyDostepu)) {
            konto.setAktywne(true);
            konto.setPotwierdzone(false);
            konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
            kontoFacade.create(konto);
            
            for (String poziomDostepuStr: poziomyDostepu) {
                PoziomDostepu poziomDostepu = PoziomDostepuManager.stworzPoziomDostepu(poziomDostepuStr);     
                poziomDostepu.setKontoId(konto);
                poziomDostepuFacade.create(poziomDostepu);
                konto.getPoziomDostepuCollection().add(poziomDostepu);
            }
        }
    }
    
    @Override
    @RolesAllowed("pobierzPodobneKonta")
    public List<Konto> znajdzPodobne(Konto konto) {
        Konto kontoWyszukaj = new Konto();
        if (konto.getImie() == null) {
            kontoWyszukaj.setImie("%%");
        } else {
            kontoWyszukaj.setImie("%" + konto.getImie() + "%");
        }
        
        if (konto.getNazwisko()== null) {
            kontoWyszukaj.setNazwisko("%%");
        } else {
            kontoWyszukaj.setNazwisko("%" + konto.getNazwisko()+ "%");
        }
        
        if (konto.getEmail()== null) {
            kontoWyszukaj.setEmail("%%");
        } else {
            kontoWyszukaj.setEmail("%" + konto.getEmail()+ "%");
        }
        
        if (konto.getTelefon()== null) {
            kontoWyszukaj.setTelefon("%%");
        } else {
            kontoWyszukaj.setTelefon("%" + konto.getTelefon()+ "%");
        }
        kontoWyszukaj.setAktywne(konto.getAktywne());
        kontoWyszukaj.setPotwierdzone(konto.getPotwierdzone());
        
        return kontoFacade.znajdzPodobne(kontoWyszukaj);
    }

    @Override
    @RolesAllowed("dodajPoziomDostepu")
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        if (PoziomDostepuManager.czyPosiadaPoziomDostepu(konto, poziom)) {
            // Posiadamy dany poziom
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            // Sprawdzamy czy poziom jest aktywny i czy możemy dołączyć dany poziom
            if (!aktualnyPoziom.getAktywny() && PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                // Jeśli tak aktywujemy posiadany już poziom
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(true);
            } else {
                // Jeśli nie zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        } else {
            // Nie posiadamy danego poziomu dostępu
            // Sprawdzamy czy możemy taki poziom dodać
            if (PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                Konto aktualneKonto = kontoFacade.znajdzPoLoginie(konto.getLogin());
                //Tworzymy i dodajemy nowy poziom dostępu
                PoziomDostepu nowyPoziom = PoziomDostepuManager.stworzPoziomDostepu(poziom);
                nowyPoziom.setKontoId(aktualneKonto);
                nowyPoziom.setAktywny(true);
                poziomDostepuFacade.create(nowyPoziom);

                aktualneKonto.getPoziomDostepuCollection().add(nowyPoziom);
            } else {                
                // Jeśli nie udało się dodać poziom dostępu zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        }
    }

    @Override
    @RolesAllowed("odlaczPoziomDostepu")
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        if (PoziomDostepuManager.czyPosiadaPoziomDostepu(konto, poziom)) {
            
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            
            if (aktualnyPoziom.getAktywny()) {
                // Jeśli poziom jest aktywny to go dezaktywujemy
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(false);
            } else {
                // Jeśli poziom jest nieaktywny zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        } else {            
            // Jeśli nie posiadamy danego poziomu dostępu zwracamy błąd
            throw new Exception("Nie możemy dodać poziomu dostępu");
        }
    }
}
