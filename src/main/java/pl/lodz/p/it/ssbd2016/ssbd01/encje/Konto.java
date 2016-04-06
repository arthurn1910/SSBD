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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author java
 */
@Entity
@Table(name = "konto")
@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
    @NamedQuery(name = "Konto.findById", query = "SELECT k FROM Konto k WHERE k.id = :id"),
    @NamedQuery(name = "Konto.findByLogin", query = "SELECT k FROM Konto k WHERE k.login = :login"),
    @NamedQuery(name = "Konto.findByHaslo", query = "SELECT k FROM Konto k WHERE k.haslo = :haslo"),
    @NamedQuery(name = "Konto.findByPotwierdzone", query = "SELECT k FROM Konto k WHERE k.potwierdzone = :potwierdzone"),
    @NamedQuery(name = "Konto.findByAktywne", query = "SELECT k FROM Konto k WHERE k.aktywne = :aktywne"),
    @NamedQuery(name = "Konto.findByVersion", query = "SELECT k FROM Konto k WHERE k.version = :version")})
public class Konto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "haslo")
    private String haslo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "potwierdzone")
    private boolean potwierdzone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywne")
    private boolean aktywne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgenta")
    private Collection<Ogloszenie> ogloszenieAgentaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idWlasciciela")
    private Collection<Ogloszenie> ogloszenieWlascicielaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kontoId")
    private Collection<PoziomDostepu> poziomDostepuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKonta")
    private Collection<Ulubione> ulubioneCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUzytkownika")
    private Collection<Spotkanie> spotkanieCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "konto")
    private DanePersonalne danePersonalne;

    public Konto() {
    }

    public Konto(Long id) {
        this.id = id;
    }

    public Konto(Long id, String login, String haslo, boolean potwierdzone, boolean aktywne, long version) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.potwierdzone = potwierdzone;
        this.aktywne = aktywne;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public boolean getPotwierdzone() {
        return potwierdzone;
    }

    public void setPotwierdzone(boolean potwierdzone) {
        this.potwierdzone = potwierdzone;
    }

    public boolean getAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Collection<Ogloszenie> getOgloszenieAgentaCollection() {
        return ogloszenieAgentaCollection;
    }

    public void setOgloszenieAgentaCollection(Collection<Ogloszenie> ogloszenieAgentaCollection) {
        this.ogloszenieAgentaCollection = ogloszenieAgentaCollection;
    }

    public Collection<Ogloszenie> getOgloszenieWlascicielaCollection() {
        return ogloszenieWlascicielaCollection;
    }

    public void setOgloszenieWlascicielaCollection(Collection<Ogloszenie> ogloszenieWlascicielaCollection) {
        this.ogloszenieWlascicielaCollection = ogloszenieWlascicielaCollection;
    }

    public Collection<PoziomDostepu> getPoziomDostepuCollection() {
        return poziomDostepuCollection;
    }

    public void setPoziomDostepuCollection(Collection<PoziomDostepu> poziomDostepuCollection) {
        this.poziomDostepuCollection = poziomDostepuCollection;
    }

    public Collection<Ulubione> getUlubioneCollection() {
        return ulubioneCollection;
    }

    public void setUlubioneCollection(Collection<Ulubione> ulubioneCollection) {
        this.ulubioneCollection = ulubioneCollection;
    }

    public Collection<Spotkanie> getSpotkanieCollection() {
        return spotkanieCollection;
    }

    public void setSpotkanieCollection(Collection<Spotkanie> spotkanieCollection) {
        this.spotkanieCollection = spotkanieCollection;
    }

    public DanePersonalne getDanePersonalne() {
        return danePersonalne;
    }

    public void setDanePersonalne(DanePersonalne danePersonalne) {
        this.danePersonalne = danePersonalne;
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
        if (!(object instanceof Konto)) {
            return false;
        }
        Konto other = (Konto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto[ id=" + id + " ]";
    }
    
}
