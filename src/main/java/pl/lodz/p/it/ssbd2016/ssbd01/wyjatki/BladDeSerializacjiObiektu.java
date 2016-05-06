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
public class BladDeSerializacjiObiektu extends ClassNotFoundException{

    public BladDeSerializacjiObiektu(String s, Throwable ex) {
        super(s, ex);
    }   
}
