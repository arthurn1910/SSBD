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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016ssbd01.mok.utils.PoziomDostepuManager;

/**
 *
 * @author java
 */
@Stateless
public class KontoManager implements KontoManagerLocal {
    
    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;   
    
    @Override
    public void rejestrujKontoKlienta(Konto konto) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        konto.setHaslo(generateMD5Hash(konto.getHaslo()));
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
    public boolean utworzKonto(Konto konto, List<String> poziomyDostepu) {
        if (PoziomDostepuManager.czyPoprawnaKombinacjaPoziomowDostepu(poziomyDostepu)) {
            konto.setAktywne(true);
            konto.setPotwierdzone(false);
            konto.setHaslo(generateMD5Hash(konto.getHaslo()));
            kontoFacade.create(konto);
            
            for (String poziomDostepuStr: poziomyDostepu) {
                PoziomDostepu poziomDostepu = PoziomDostepuManager.stworzPoziomDostepu(poziomDostepuStr);     
                poziomDostepu.setKontoId(konto);
                konto.getPoziomDostepuCollection().add(poziomDostepu);
                poziomDostepuFacade.create(poziomDostepu);
            }
            return true;
        }
        else 
            return false;
    }

    
    @Override
    public String generateMD5Hash(String haslo) {
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
