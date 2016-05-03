/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import javax.naming.NamingException;

/**
 *
 * @author java
 */
public class NiewykonanaOperacja extends NamingException{
    private String miejsce;
    private String operacja;

    public NiewykonanaOperacja(String miejsce, String operacja) {
        super();
        this.miejsce = miejsce;
        this.operacja=operacja;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public String getOperacja() {
        return operacja;
    }
    
    

}
