/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import javax.ejb.ApplicationException;
import javax.ejb.EJBAccessException;

/**
 *
 * @author java
 */
@ApplicationException(rollback = true)
public class BrakDostepu extends EJBAccessException{
    private String miejsce;

    public BrakDostepu(String miejsce) {
        super();
        this.miejsce = miejsce;
    }

    public String getMiejsce() {
        return miejsce;
    }
    
    
}
