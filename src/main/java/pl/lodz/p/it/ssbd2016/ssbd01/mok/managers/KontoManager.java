package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.ExteriorInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.MD5Generator;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Klasa pośrednicząca miedzy MOKEndpoint a fasadami. Przetwarza niezbędne dane.
 * Implementuje interfejs KontoManagaerLocal
 */
@Stateless
@Interceptors({ExteriorInterceptor.class,TrackerInterceptor.class})
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
    public void zmienMojeHaslo(Konto konto, String noweHaslo, String stareHasloWpisane) throws WyjatekSystemu{
        if (!konto.getLogin().equals(sessionContext.getCallerPrincipal().getName())) {
            throw new WyjatekSystemu("blad.niezgodnyLogin");
        }
        String stareHaslo = konto.getHaslo();
        String hashedPassword = null;

        hashedPassword = MD5Generator.generateMD5Hash(noweHaslo);
        stareHasloWpisane = MD5Generator.generateMD5Hash(stareHasloWpisane);
        if (stareHasloWpisane.equals(stareHaslo)) {
            konto.setHaslo(hashedPassword);
            kontoFacade.edit(konto);
        } else {
            throw new WyjatekSystemu("blad.niezgodneHasla");
        }
    }

    @Override
    @RolesAllowed("zmienHaslo")
    public void zmienHaslo(Konto konto, String noweHaslo) throws WyjatekSystemu{
            String noweZahashowanehaslo = MD5Generator.generateMD5Hash(noweHaslo);
            konto.setHaslo(noweZahashowanehaslo);
            kontoFacade.edit(konto);
    }
    
    @Override
    @PermitAll
    public void rejestrujKontoKlienta(Konto konto) throws WyjatekSystemu{
        try{
            konto.setAktywne(true);
            konto.setPotwierdzone(false);
            konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
            kontoFacade.create(konto);
            PoziomDostepuManager tmp=new PoziomDostepuManager();
            PoziomDostepu poziomDostepu = tmp.stworzPoziomDostepuKlient();
            poziomDostepu.setKontoId(konto);
            
            poziomDostepuFacade.create(poziomDostepu);
            
            konto.getPoziomDostepuCollection().add(poziomDostepu);
        }catch(EJBException ex){
            throw new WyjatekSystemu("blad.naruszenieUniq",ex);
        }
    }
    
    @Override
    @RolesAllowed("utworzKonto")
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) throws WyjatekSystemu{
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        if (tmp.czyPoprawnaKombinacjaPoziomowDostepu(poziomyDostepu)) {
            try{
                konto.setAktywne(true);
                konto.setPotwierdzone(false);
                konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
                kontoFacade.create(konto);

                for (String poziomDostepuStr: poziomyDostepu) {
                    PoziomDostepu poziomDostepu = tmp.stworzPoziomDostepu(poziomDostepuStr);     
                    poziomDostepu.setKontoId(konto);
                    poziomDostepuFacade.create(poziomDostepu);
                    konto.getPoziomDostepuCollection().add(poziomDostepu);
                }
            }catch(EJBException ex){
                throw new WyjatekSystemu("blad.naruszenieUniq",ex);
            }
        }
    }
    
    @Override
    @RolesAllowed("pobierzWszystkieKonta")
    public List<Konto> pobierzWszystkie() {
        return kontoFacade.findAll();
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
    public void dodajPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu{
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        if (tmp.czyPosiadaPoziomDostepu(konto, poziom)) {
            // Posiadamy dany poziom
            PoziomDostepu aktualnyPoziom = tmp.pobierzPoziomDostepu(konto, poziom);
            // Sprawdzamy czy poziom jest aktywny i czy możemy dołączyć dany poziom
            if (!aktualnyPoziom.getAktywny() && tmp.czyMoznaDodacPoziom(konto, poziom)) {
                // Jeśli tak aktywujemy posiadany już poziom
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(true);
            } else {
                // Jeśli nie zwracamy błąd
                throw new WyjatekSystemu("blad.PoziomDostepuDodanie");
            }
        } else {
            // Nie posiadamy danego poziomu dostępu
            // Sprawdzamy czy możemy taki poziom dodać
            if (tmp.czyMoznaDodacPoziom(konto, poziom)) {
                Konto aktualneKonto = kontoFacade.znajdzPoLoginie(konto.getLogin());
                //Tworzymy i dodajemy nowy poziom dostępu
                PoziomDostepu nowyPoziom = tmp.stworzPoziomDostepu(poziom);
                nowyPoziom.setKontoId(aktualneKonto);
                nowyPoziom.setAktywny(true);
                poziomDostepuFacade.create(nowyPoziom);

                aktualneKonto.getPoziomDostepuCollection().add(nowyPoziom);
            } else {                
                // Jeśli nie udało się dodać poziom dostępu zwracamy błąd
                throw new WyjatekSystemu("blad.PoziomDostepuDodanie");
            }
        }
    }

    @Override
    @RolesAllowed("odlaczPoziomDostepu")
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu{
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        if (tmp.czyPosiadaPoziomDostepu(konto, poziom)) {
            
            PoziomDostepu aktualnyPoziom = tmp.pobierzPoziomDostepu(konto, poziom);
            
            if (aktualnyPoziom.getAktywny()) {
                // Jeśli poziom jest aktywny to go dezaktywujemy
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(false);
            } else {
                // Jeśli poziom jest nieaktywny zwracamy błąd
                throw new WyjatekSystemu("blad.PoziomDostepuOlaczenie");

            }
        } else {            
            // Jeśli nie posiadamy danego poziomu dostępu zwracamy błąd
            throw new WyjatekSystemu("blad.PoziomDostepuOdlaczenie");
        }
    }
}
