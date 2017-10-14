 
import ga.protoredes.protoredes.controlador.ArticuloJpaController;
import ga.protoredes.protoredes.modelo.Articulo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
@ManagedBean(name = "verArticulos")
@ViewScoped
public class VerArticulos implements Serializable {
     
    ArticuloJpaController a= new ArticuloJpaController(Persistence.createEntityManagerFactory("ga.protoredes_Protoredes_war_1.0-SNAPSHOTPU"));
        
    private List<Articulo> articulos=a.findArticuloEntities();

    public void setArticulos(List<Articulo> articulos) {
        System.out.println(articulos);
        this.articulos = articulos;
    }
    
    public List<Articulo> getArticulos() {
        System.out.println(articulos);
         return articulos;
    }
 
}