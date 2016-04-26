/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.manager.KontoManagerLocal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author Patryk
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal{

    @EJB
    private KontoManagerLocal kontoManager;
    @EJB
    private KontoFacadeLocal kontoFacade;
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;

    private Konto kontoStan;
    @Resource
    private SessionContext sessionContext;

    @Override
    public void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        kontoFacade.create(konto);

        poziomDostepu.setKontoId(konto);
        poziomDostepuFacade.create(poziomDostepu);
        konto.getPoziomDostepuCollection().add(poziomDostepu);


    }

    @Override
    public List<Konto> pobierzWszystkieKonta() {
        return kontoFacade.findAll();
    }

    @Override
    public void potwierdzKonto(Konto konto) {
        Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);
    }


    @Override
    public void odblokujKonto(Konto rowData) {
        Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(true);
    }

    @Override
    public void zablokujKonto(Konto rowData) {
        Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(false);
    }

    @Override
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        kontoManager.zmienMojeHasloJesliPoprawne(noweHaslo, stareHaslo);
    }

    @Override
    public void zmienHaslo(Konto konto, String noweHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        kontoManager.zmienHaslo(konto, noweHaslo);
    }


    @Override
    public Konto znajdzPoLoginie(String login) {
        return kontoFacade.findByLogin(login);
    }

    @Override
    public Konto pobierzKontoDoEdycji(Konto konto) {
        kontoStan = kontoFacade.find(konto.getId());
        try {
            return (Konto) CloneUtils.deepCloneThroughSerialization(kontoStan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void zapiszKontoPoEdycji(Konto konto) {
        if (kontoStan == null) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        if (!kontoStan.equals(konto)) {
            throw new IllegalArgumentException("Modyfikowane konto niezgodne z wczytanym");
        }
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setImie(konto.getImie());
        kontoStan.setEmail(konto.getEmail());
        kontoStan.setNazwisko(konto.getNazwisko());
        kontoFacade.edit(kontoStan);
        kontoStan = null;
    }


}
