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
}
