/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author java
 */
@Entity
@Table(name = "nieruchomosc_posiada")
@NamedQueries({
    @NamedQuery(name = "NieruchomoscPosiada.findAll", query = "SELECT n FROM NieruchomoscPosiada n"),
    @NamedQuery(name = "NieruchomoscPosiada.findById", query = "SELECT n FROM NieruchomoscPosiada n WHERE n.id = :id")})
public class NieruchomoscPosiada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "id_nieruchomosci", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nieruchomosc idNieruchomosci;
    @JoinColumn(name = "id_wartosci", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private WartosciDlaKategorii idWartosci;

    public NieruchomoscPosiada() {
    }

    public NieruchomoscPosiada(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nieruchomosc getIdNieruchomosci() {
        return idNieruchomosci;
    }

    public void setIdNieruchomosci(Nieruchomosc idNieruchomosci) {
        this.idNieruchomosci = idNieruchomosci;
    }

    public WartosciDlaKategorii getIdWartosci() {
        return idWartosci;
    }

    public void setIdWartosci(WartosciDlaKategorii idWartosci) {
        this.idWartosci = idWartosci;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NieruchomoscPosiada)) {
            return false;
        }
        NieruchomoscPosiada other = (NieruchomoscPosiada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.NieruchomoscPosiada[ id=" + id + " ]";
    }
    
}
