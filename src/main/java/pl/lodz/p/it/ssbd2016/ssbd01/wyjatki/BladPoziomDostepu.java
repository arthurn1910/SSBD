/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import javax.ejb.ApplicationException;

/**
 *
 * @author java
 */
@ApplicationException(rollback = true)
public class BladPoziomDostepu extends Exception{
    private String poziom;
    private int status;
      
    public BladPoziomDostepu(String poziom, int status) {
        super();
        this.poziom = poziom;
        this.status=status;
    }

    public String getPoziom() {
        return poziom;
    }

    public int getStatus() {
        return status;
    }
}
