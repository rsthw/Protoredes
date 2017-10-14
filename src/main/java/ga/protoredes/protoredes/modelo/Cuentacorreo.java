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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonio
 */
@Entity
@Table(name = "cuentacorreo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentacorreo.findAll", query = "SELECT c FROM Cuentacorreo c")
    , @NamedQuery(name = "Cuentacorreo.findById", query = "SELECT c FROM Cuentacorreo c WHERE c.id = :id")
    , @NamedQuery(name = "Cuentacorreo.findByCorreo", query = "SELECT c FROM Cuentacorreo c WHERE c.correo = :correo")
    , @NamedQuery(name = "Cuentacorreo.findByContrasena", query = "SELECT c FROM Cuentacorreo c WHERE c.contrasena = :contrasena")})
public class Cuentacorreo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Contrasena")
    private String contrasena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "correoId")
    private List<Usuario> usuarioList;

    public Cuentacorreo() {
    }

    public Cuentacorreo(Integer id) {
        this.id = id;
    }

    public Cuentacorreo(Integer id, String correo, String contrasena) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
        if (!(object instanceof Cuentacorreo)) {
            return false;
        }
        Cuentacorreo other = (Cuentacorreo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ga.protoredes.protoredes.modelo.Cuentacorreo[ id=" + id + " ]";
    }
    
}
