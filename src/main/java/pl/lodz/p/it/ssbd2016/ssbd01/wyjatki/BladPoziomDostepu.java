/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

/**
 *
 * @author java
 */
public class BladPoziomDostepu extends Exception{
    private String miejsce;
    private String poziom;
    private int status;

    public BladPoziomDostepu(String miejsce, String poziom, int status) {
        super();
        this.miejsce = miejsce;
        this.poziom = poziom;
        this.status=status;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public String getPoziom() {
        return poziom;
    }

    public int getStatus() {
        return status;
    }
    
    
    
}
