
package pl.lodz.p.it.ssbd2016.ssbd01.interceptors;


import java.rmi.RemoteException;
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
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }catch(RemoteException e){
            WyjatekSystemu ex=new WyjatekSystemu("RemoteException", e);
            throw ex;
        }catch (NullPointerException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("nullPointerException", e);
            throw exc;
        }catch (EJBAccessException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("accessException", e);
            throw exc;
        }catch (EJBTransactionRequiredException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("transactionRequiredException", e);
            throw exc;
        }catch (EJBTransactionRolledbackException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("transactionRolledbackException", e);
            throw exc;
        } catch (EJBException e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("EJBException", e);
            throw exc;
        }catch (Exception e) {
            Logger.getLogger(ExteriorInterceptor.class.getName()).log(Level.SEVERE, null, e);
            WyjatekSystemu exc=new WyjatekSystemu("exception", e);
            throw exc;
        } 
    }
}
