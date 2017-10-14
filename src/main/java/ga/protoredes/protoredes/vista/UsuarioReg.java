/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.vista;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import ga.protoredes.protoredes.modelo.*;
import ga.protoredes.protoredes.controlador.*;

import java.io.Serializable;
import java.sql.DriverManager;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author SANTIAGO LEY
 */
@ManagedBean(name = "usuarioReg")
@SessionScoped
public class UsuarioReg implements Serializable {

   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ga.protoredes_Protoredes_war_1.0-SNAPSHOTPU");
    EntityManager em = emf.createEntityManager();
   public Tarjeta tarjeta = new Tarjeta();
   public Cuentacorreo correo = new Cuentacorreo();
   public Usuario usuario = new Usuario();

    public Cuentacorreo getCorreo() {
        return correo;
    }

    public void setCorreo(Cuentacorreo correo) {
        this.correo = correo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
   
   public Tarjeta getTarjeta() {
        return tarjeta;
   }
   public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
   }
   
    public String registraUsuario(){
      TarjetaJpaController controladorTarjeta  = new TarjetaJpaController(emf);
      CuentacorreoJpaController controladorCorreo  = new CuentacorreoJpaController(emf);
      UsuarioJpaController controladorUsuario  = new UsuarioJpaController(emf);
        
      controladorTarjeta.create(tarjeta);
      int indiceTarjeta = controladorTarjeta.findTarjetaByNumeroTarjeta(tarjeta.getNumeroTarjeta()).getId();
      usuario.setTarjetaId(controladorTarjeta.findTarjeta(indiceTarjeta));
      
      controladorCorreo.create(correo);
      int indiceCorreo = controladorCorreo.findCuentaByCorreo(correo.getCorreo()).getId();
      usuario.setCorreoId(controladorCorreo.findCuentacorreo(indiceCorreo));
      controladorUsuario.create(usuario);
       
      return "index.xhtml?faces-redirect=true";
   }
   
}
