/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.vista;

import ga.protoredes.protoredes.modelo.*;
import ga.protoredes.protoredes.controlador.*;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author SANTIAGO LEY
 */
@ManagedBean(name = "agregaArticulo")
@SessionScoped
public class AgregaArticulo implements Serializable  {
   private UploadedFile imagen;
   public String direccion="/home/antonio/Documents/Redes/";
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ga.protoredes_Protoredes_war_1.0-SNAPSHOTPU");
   EntityManager em = emf.createEntityManager();
   
   public Articulo articulo = new Articulo();

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
     
    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
   
   
    public void insertaImagen(){
        try{
        Path folder = Paths.get(direccion+"Protoredes/src/main/webapp/assets/img/imagenesArticulos/");
        String filename = FilenameUtils.getBaseName(imagen.getFileName());
        String extension = FilenameUtils.getExtension(imagen.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        InputStream input = imagen.getInputstream();
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        
        String [] tmp = file.toString().split("imagenesArticulos/");
        articulo.setImagen(tmp[1]);
        }catch(IOException e){e.printStackTrace();}
        
        
    }
    public String registraArticulo(){
      ArticuloJpaController controlador  = new ArticuloJpaController(emf);
      insertaImagen();
      controlador.create(articulo);
      return "index.xhtml?faces-redirect=true";
   }
   
}
