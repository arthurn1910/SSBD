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
@Table(name = "katergorie")
@NamedQueries({
    @NamedQuery(name = "Katergorie.findAll", query = "SELECT k FROM Katergorie k"),
    @NamedQuery(name = "Katergorie.findById", query = "SELECT k FROM Katergorie k WHERE k.id = :id"),
    @NamedQuery(name = "Katergorie.findByNazwa", query = "SELECT k FROM Katergorie k WHERE k.nazwa = :nazwa"),
    @NamedQuery(name = "Katergorie.findByVersion", query = "SELECT k FROM Katergorie k WHERE k.version = :version")})
public class Katergorie implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKategorii")
    private Collection<WartosciDlaKategorii> wartosciDlaKategoriiCollection;

    public Katergorie() {
    }

    public Katergorie(Long id) {
        this.id = id;
    }

    public Katergorie(Long id, String nazwa, long version) {
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

    public Collection<WartosciDlaKategorii> getWartosciDlaKategoriiCollection() {
        return wartosciDlaKategoriiCollection;
    }

    public void setWartosciDlaKategoriiCollection(Collection<WartosciDlaKategorii> wartosciDlaKategoriiCollection) {
        this.wartosciDlaKategoriiCollection = wartosciDlaKategoriiCollection;
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
        if (!(object instanceof Katergorie)) {
            return false;
        }
        Katergorie other = (Katergorie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.Katergorie[ id=" + id + " ]";
    }
    
}
