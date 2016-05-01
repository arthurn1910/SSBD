package pl.lodz.p.it.ssbd2016.ssbd01.interceptors;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Interceptor przechwytujący wywołania metod i logujący ich wywołania.
 */
public class TrackerInterceptor {
    
    @Resource
    private SessionContext sctx;
    private static final Logger loger = Logger.getLogger(TrackerInterceptor.class.getName());
    
    @AroundInvoke
    public Object traceInvoke(InvocationContext ictx) throws Exception {
        StringBuilder message = new StringBuilder("Przechwycone wywołanie metody: ");
        message.append(ictx.getMethod().toString());
        message.append(" użytkownik: " + sctx.getCallerPrincipal().getName());
        message.append(" wartości parametrów: ");
        
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
        } catch (Exception e) {
            message.append(" zakończone wyjątkiem: " + e.toString());
            loger.severe(message.toString());
            throw e;
        } 
        
        message.append(" wartość zwrócona: ");
        if (null == result) {
            message.append("null ");
        } else {
            message.append(result.toString() + " ");
        }
        loger.severe(message.toString());
        
        return result;
    }
}
