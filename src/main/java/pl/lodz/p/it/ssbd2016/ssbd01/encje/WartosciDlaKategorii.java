/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author java
 */
@Entity
@Table(name = "wartosci_dla_kategorii")
@NamedQueries({
    @NamedQuery(name = "WartosciDlaKategorii.findAll", query = "SELECT w FROM WartosciDlaKategorii w"),
    @NamedQuery(name = "WartosciDlaKategorii.findById", query = "SELECT w FROM WartosciDlaKategorii w WHERE w.id = :id"),
    @NamedQuery(name = "WartosciDlaKategorii.findByNazwa", query = "SELECT w FROM WartosciDlaKategorii w WHERE w.nazwa = :nazwa"),
    @NamedQuery(name = "WartosciDlaKategorii.findByVersion", query = "SELECT w FROM WartosciDlaKategorii w WHERE w.version = :version")})
public class WartosciDlaKategorii implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nazwa")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idWartosci")
    private Collection<NieruchomoscPosiada> nieruchomoscPosiadaCollection;
    @JoinColumn(name = "id_kategorii", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Katergorie idKategorii;

    public WartosciDlaKategorii() {
    }

    public WartosciDlaKategorii(Long id) {
        this.id = id;
    }

    public WartosciDlaKategorii(Long id, String nazwa, long version) {
        this.id = id;
        this.nazwa = nazwa;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Collection<NieruchomoscPosiada> getNieruchomoscPosiadaCollection() {
        return nieruchomoscPosiadaCollection;
    }

    public void setNieruchomoscPosiadaCollection(Collection<NieruchomoscPosiada> nieruchomoscPosiadaCollection) {
        this.nieruchomoscPosiadaCollection = nieruchomoscPosiadaCollection;
    }

    public Katergorie getIdKategorii() {
        return idKategorii;
    }

    public void setIdKategorii(Katergorie idKategorii) {
        this.idKategorii = idKategorii;
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
        if (!(object instanceof WartosciDlaKategorii)) {
            return false;
        }
        WartosciDlaKategorii other = (WartosciDlaKategorii) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.WartosciDlaKategorii[ id=" + id + " ]";
    }
    
}
