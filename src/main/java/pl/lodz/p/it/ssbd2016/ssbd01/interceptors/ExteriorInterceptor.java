
package pl.lodz.p.it.ssbd2016.ssbd01.interceptors;


import java.rmi.RemoteException;
import java.sql.SQLException;
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
import javax.mail.MessagingException;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

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
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu 
     */
    @AroundInvoke
    public Object traceInvoke(InvocationContext ictx) throws WyjatekSystemu{
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
        }catch (WyjatekSystemu e) {
            loger.log(Level.SEVERE, "Złapany wyjątek WyjatekSystemu w "+ExteriorInterceptor.class.getName(), e);
            throw e;
        }catch(RemoteException e){
            loger.log(Level.SEVERE, "Złapany wyjątek RemoteException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu ex=new WyjatekSystemu("blad.RemoteException", e);
            throw ex;
        }catch (NullPointerException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek NullPointerException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.NullPointerException", e);
            throw exc;
        }catch (EJBAccessException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek EJBAccessException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.EJBAccessException", e);
            throw exc;
        }catch (EJBTransactionRequiredException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek EJBTransactionRequiredException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.EJBTransactionRequiredException", e);
            throw exc;
        }catch (EJBTransactionRolledbackException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek EJBTransactionRolledbackException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.EJBTransactionRolledbackException", e);
            throw exc;
        }catch (SQLException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek SQLException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.SQLException", e);
            throw exc;
        }catch (MessagingException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek MessagingException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.wysylanieWidaomosci",e);
            throw exc;
        
        }catch (EJBException e) {
            loger.log(Level.SEVERE, "Złapany wyjątek EJBException w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.EJBException", e);
            throw exc;
        }catch (Exception e) {
            loger.log(Level.SEVERE, "Złapany wyjątek Exception w "+ExteriorInterceptor.class.getName(), e);
            WyjatekSystemu exc=new WyjatekSystemu("blad.nieobsluzonyWyjatek", e);
            throw exc;
        } 
    }
}
