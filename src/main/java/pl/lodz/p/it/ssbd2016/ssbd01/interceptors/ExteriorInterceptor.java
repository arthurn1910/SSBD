
package pl.lodz.p.it.ssbd2016.ssbd01.interceptors;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRequiredException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladWywolania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.KontoNiezgodneWczytanym;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiewykonanaOperacja;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje;

/**
 * Interceptor zewnętrzny 
 */
public class ExteriorInterceptor {
    
    @Resource
    private SessionContext sctx;
    private static final Logger loger = Logger.getLogger(TrackerInterceptor.class.getName());
    /***
     * Funkcja przechwytująca wywołania wyjatków, opakowywania wyjątków nieweryfikowalnych,
     * logująca ich wywołania oraz przesyłająca wyjątek dalej.
     * @param ictx
     * @return
     * @throws BladWywolania
     * @throws BrakAlgorytmuKodowania
     * @throws BladPoziomDostepu
     * @throws BladPliku
     * @throws BladDeSerializacjiObiektu
     * @throws NiewykonanaOperacja
     * @throws PoziomDostepuNieIstnieje
     * @throws NieobslugiwaneKodowanie
     * @throws NiezgodneHasla
     * @throws NiezgodnyLogin 
     */
    @AroundInvoke
    public Object traceInvoke(InvocationContext ictx) throws BladWywolania, BrakAlgorytmuKodowania, BladPoziomDostepu, BladPliku, BladDeSerializacjiObiektu, NiewykonanaOperacja, PoziomDostepuNieIstnieje, NieobslugiwaneKodowanie, NiezgodneHasla, NiezgodnyLogin{
        StringBuilder message = new StringBuilder("Przechwycone wywołanie metody: ");
        message.append(ictx.getMethod().toString());
        message.append(" użytkownik: " + sctx.getCallerPrincipal().getName());
        message.append(" wartości parametrów: ");
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        if (ictx.getParameters() != null) {
            for (Object param : ictx.getParameters()) {
                if (null == param) {
                    message.append("null ");
                } else {
                    message.append(param.toString() + " ");
                }
            }
        }
        Object result;
        try {
            result = ictx.proceed();
            message.append(" wartość zwrócona: ");
             if (null == result) {
                message.append("null ");
            } else {
                message.append(result.toString() + " ");
            }
            loger.severe(message.toString());
        
            return result;
        }catch (BrakDostepu | KontoNiezgodneWczytanym | NiezgodnyLogin | NiezgodneHasla | BladPoziomDostepu
                | BrakAlgorytmuKodowania | BladPliku | BladDeSerializacjiObiektu | NieobslugiwaneKodowanie |
                PoziomDostepuNieIstnieje | NiewykonanaOperacja e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }catch (NullPointerException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("nullPointerException", e);
            throw exc;
        }catch (EJBAccessException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("accessException", e);
            throw exc;
        }catch (EJBTransactionRequiredException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("transactionRequiredException", e);
            throw exc;
        }catch (EJBTransactionRolledbackException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("transactionRolledbackException", e);
            throw exc;
        } catch (EJBException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("EJBException", e);
            throw exc;
        }catch (Exception e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            BladWywolania exc=new BladWywolania("exception", e);
            throw exc;
        } 
    }
}
