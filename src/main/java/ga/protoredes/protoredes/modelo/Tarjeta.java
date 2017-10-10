/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.modelo;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SANTIAGO LEY
 */
@Entity
@Table(name = "tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")
    , @NamedQuery(name = "Tarjeta.findById", query = "SELECT t FROM Tarjeta t WHERE t.id = :id")
    , @NamedQuery(name = "Tarjeta.findByNumeroTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.numeroTarjeta = :numeroTarjeta")
    , @NamedQuery(name = "Tarjeta.findByDigitosReverso", query = "SELECT t FROM Tarjeta t WHERE t.digitosReverso = :digitosReverso")
    , @NamedQuery(name = "Tarjeta.findByNip", query = "SELECT t FROM Tarjeta t WHERE t.nip = :nip")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NumeroTarjeta")
    private int numeroTarjeta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DigitosReverso")
    private int digitosReverso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Nip")
    private int nip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarjetaId")
    private List<Usuario> usuarioList;

    public Tarjeta() {
    }

    public Tarjeta(Integer id) {
        this.id = id;
    }

    public Tarjeta(Integer id, int numeroTarjeta, int digitosReverso, int nip) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.digitosReverso = digitosReverso;
        this.nip = nip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getDigitosReverso() {
        return digitosReverso;
    }

    public void setDigitosReverso(int digitosReverso) {
        this.digitosReverso = digitosReverso;
    }

    public int getNip() {
        return nip;
    }

    public void setNip(int nip) {
        this.nip = nip;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ga.protoredes.protoredes.modelo.Tarjeta[ id=" + id + " ]";
    }
    
}
