/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SANTIAGO LEY
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Contrasena")
    private String contrasena;
    @JoinTable(name = "carrito", joinColumns = {
        @JoinColumn(name = "IdUsuario", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "IdArticulo", referencedColumnName = "Id")})
    @ManyToMany
    private List<Articulo> articuloList;
    @JoinTable(name = "comprado", joinColumns = {
        @JoinColumn(name = "IdUsuario", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "IdArticulo", referencedColumnName = "Id")})
    @ManyToMany
    private List<Articulo> articuloList1;
    @JoinColumn(name = "CorreoId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Cuentacorreo correoId;
    @JoinColumn(name = "TarjetaId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Tarjeta tarjetaId;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String username, String contrasena) {
        this.id = id;
        this.username = username;
        this.contrasena = contrasena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList1() {
        return articuloList1;
    }

    public void setArticuloList1(List<Articulo> articuloList1) {
        this.articuloList1 = articuloList1;
    }

    public Cuentacorreo getCorreoId() {
        return correoId;
    }

    public void setCorreoId(Cuentacorreo correoId) {
        this.correoId = correoId;
    }

    public Tarjeta getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Tarjeta tarjetaId) {
        this.tarjetaId = tarjetaId;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ga.protoredes.protoredes.modelo.Usuario[ id=" + id + " ]";
    }
    
}
