package pl.lodz.p.it.ssbd2016.ssbd01.mok.manager;

import pl.lodz.p.it.ssbd2016.ssbd01.Utils.HashCreator;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 */
@Stateless
public class KontoManager implements KontoManagerLocal {

    private static final Logger logger = Logger.getLogger(KontoManager.class.getName());

    @EJB
    private KontoFacadeLocal kontoFacade;
    @Resource
    private SessionContext sessionContext;
    private Konto kontoDoEdycji;

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
}
