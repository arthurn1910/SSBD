/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.HashCreator;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.MD5Generator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 * Klasa pośrednicząca miedzy MOKEndpoint a fasadami. Przetwarza niezbędne dane.
 * Implementuje interfejs KontoManagaerLocal
 */
@Stateless
public class KontoManager implements KontoManagerLocal {

    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;   
    
     private static final Logger logger = Logger.getLogger(pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManager.class.getName());

    @Resource
    private SessionContext sessionContext;
    private Konto kontoDoEdycji;

    /**
     * {@inheritDoc}
     */
    @Override
    public void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHasloWpisane) {

        kontoDoEdycji = kontoFacade.findByLogin(sessionContext.getCallerPrincipal().getName());
        String stareHaslo = kontoDoEdycji.getHaslo();
        String hashedPassword = null;

        try {
            hashedPassword = HashCreator.MD5(noweHaslo);
            stareHasloWpisane = HashCreator.MD5(stareHasloWpisane);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.info("KontoManager - zmienHaslo()");
        }
        if (stareHasloWpisane.equals(stareHaslo)) {
            kontoDoEdycji.setHaslo(hashedPassword);
            kontoFacade.edit(kontoDoEdycji);
            logger.info("haslo zmienione nowy hash: " + kontoDoEdycji.getHaslo());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zmienHaslo(Konto konto, String noweHaslo) {
        kontoDoEdycji = kontoFacade.find(konto.getId());
        try {
            String noweZahashowanehaslo = HashCreator.MD5(noweHaslo);
            kontoDoEdycji.setHaslo(noweZahashowanehaslo);
            kontoFacade.edit(kontoDoEdycji);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.info("KontoManager - zmienHaslo()");
        }

    }
    
    @Override
    public void rejestrujKontoKlienta(Konto konto) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
        try{
        kontoFacade.create(konto);
        }
        catch(Exception e)
        {
            
        }
        PoziomDostepu poziomDostepu = PoziomDostepuManager.stworzPoziomDostepuKlient();
        poziomDostepu.setKontoId(konto);
        poziomDostepuFacade.create(poziomDostepu);
        konto.getPoziomDostepuCollection().add(poziomDostepu);
    }
    
    @Override
    public void utworzKonto(Konto konto, List<String> poziomyDostepu) {
        if (PoziomDostepuManager.czyPoprawnaKombinacjaPoziomowDostepu(poziomyDostepu)) {
            konto.setAktywne(true);
            konto.setPotwierdzone(false);
            konto.setHaslo(MD5Generator.generateMD5Hash(konto.getHaslo()));
            kontoFacade.create(konto);
            
            for (String poziomDostepuStr: poziomyDostepu) {
                PoziomDostepu poziomDostepu = PoziomDostepuManager.stworzPoziomDostepu(poziomDostepuStr);     
                poziomDostepu.setKontoId(konto);
                konto.getPoziomDostepuCollection().add(poziomDostepu);
                poziomDostepuFacade.create(poziomDostepu);
            }
        }
    }
    
    @Override
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
                poziomDostepuFacade.create(nowyPoziom);

                aktualneKonto.getPoziomDostepuCollection().add(nowyPoziom);
                nowyPoziom.setKontoId(aktualneKonto);
                nowyPoziom.setAktywny(true);
            } else {                
                // Jeśli nie udało się dodać poziom dostępu zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        }
    }

    @Override
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        if (PoziomDostepuManager.czyPosiadaAktywnyPoziomDostepu(konto, poziom)) {
            
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
            // Jeśli nie udało się odłączyć poziom dostępu zwracamy błąd
            throw new Exception("Nie możemy dodać poziomu dostępu");
        }
    }
    
}
