/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.vista;

import ga.protoredes.protoredes.modelo.*;
import ga.protoredes.protoredes.controlador.*;

import java.io.Serializable;
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
@ManagedBean(name = "usuarioDAO")
@SessionScoped
public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ga.protoredes_Protoredes_war_1.0-SNAPSHOTPU");
    
    private Usuario persona = new Usuario();

    private String correo = "";
    private String password = "";

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getName(){
        Usuario us = (Usuario)UtilidadesSesion.getSession().getAttribute("persona_usuario");
        if(us == null) return "";
        return us.getUsername();
    }

    public String iniciarSesion() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuario> query = em.createQuery("SELECT p FROM Usuario p WHERE p.username = \'" + correo + 
                "\' AND p.contrasena = \'" + password + "\'", Usuario.class);
        List<Usuario> results = query.getResultList();

        if (results.isEmpty()) {
            return "login.xhtml?faces-redirect=true";
        }
        persona = results.get(0);
        if(persona != null)
            UtilidadesSesion.getSession().setAttribute("persona_usuario", persona);
        
        return "index.xhtml?faces-redirect=true";
}

    public String cerrarSesion() {
        HttpSession session = UtilidadesSesion.getSession();
        session.removeAttribute("persona_usuario");
        return "index.xhtml?faces-redirect=true";
    }

    
}
