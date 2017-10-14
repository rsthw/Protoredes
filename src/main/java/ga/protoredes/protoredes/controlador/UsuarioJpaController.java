/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.controlador;

import ga.protoredes.protoredes.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ga.protoredes.protoredes.modelo.Cuentacorreo;
import ga.protoredes.protoredes.modelo.Tarjeta;
import ga.protoredes.protoredes.modelo.Articulo;
import ga.protoredes.protoredes.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonio
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getArticuloList() == null) {
            usuario.setArticuloList(new ArrayList<Articulo>());
        }
        if (usuario.getArticuloList1() == null) {
            usuario.setArticuloList1(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuentacorreo correoId = usuario.getCorreoId();
            if (correoId != null) {
                correoId = em.getReference(correoId.getClass(), correoId.getId());
                usuario.setCorreoId(correoId);
            }
            Tarjeta tarjetaId = usuario.getTarjetaId();
            if (tarjetaId != null) {
                tarjetaId = em.getReference(tarjetaId.getClass(), tarjetaId.getId());
                usuario.setTarjetaId(tarjetaId);
            }
            List<Articulo> attachedArticuloList = new ArrayList<Articulo>();
            for (Articulo articuloListArticuloToAttach : usuario.getArticuloList()) {
                articuloListArticuloToAttach = em.getReference(articuloListArticuloToAttach.getClass(), articuloListArticuloToAttach.getId());
                attachedArticuloList.add(articuloListArticuloToAttach);
            }
            usuario.setArticuloList(attachedArticuloList);
            List<Articulo> attachedArticuloList1 = new ArrayList<Articulo>();
            for (Articulo articuloList1ArticuloToAttach : usuario.getArticuloList1()) {
                articuloList1ArticuloToAttach = em.getReference(articuloList1ArticuloToAttach.getClass(), articuloList1ArticuloToAttach.getId());
                attachedArticuloList1.add(articuloList1ArticuloToAttach);
            }
            usuario.setArticuloList1(attachedArticuloList1);
            em.persist(usuario);
            if (correoId != null) {
                correoId.getUsuarioList().add(usuario);
                correoId = em.merge(correoId);
            }
            if (tarjetaId != null) {
                tarjetaId.getUsuarioList().add(usuario);
                tarjetaId = em.merge(tarjetaId);
            }
            for (Articulo articuloListArticulo : usuario.getArticuloList()) {
                articuloListArticulo.getUsuarioList().add(usuario);
                articuloListArticulo = em.merge(articuloListArticulo);
            }
            for (Articulo articuloList1Articulo : usuario.getArticuloList1()) {
                articuloList1Articulo.getUsuarioList().add(usuario);
                articuloList1Articulo = em.merge(articuloList1Articulo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Cuentacorreo correoIdOld = persistentUsuario.getCorreoId();
            Cuentacorreo correoIdNew = usuario.getCorreoId();
            Tarjeta tarjetaIdOld = persistentUsuario.getTarjetaId();
            Tarjeta tarjetaIdNew = usuario.getTarjetaId();
            List<Articulo> articuloListOld = persistentUsuario.getArticuloList();
            List<Articulo> articuloListNew = usuario.getArticuloList();
            List<Articulo> articuloList1Old = persistentUsuario.getArticuloList1();
            List<Articulo> articuloList1New = usuario.getArticuloList1();
            if (correoIdNew != null) {
                correoIdNew = em.getReference(correoIdNew.getClass(), correoIdNew.getId());
                usuario.setCorreoId(correoIdNew);
            }
            if (tarjetaIdNew != null) {
                tarjetaIdNew = em.getReference(tarjetaIdNew.getClass(), tarjetaIdNew.getId());
                usuario.setTarjetaId(tarjetaIdNew);
            }
            List<Articulo> attachedArticuloListNew = new ArrayList<Articulo>();
            for (Articulo articuloListNewArticuloToAttach : articuloListNew) {
                articuloListNewArticuloToAttach = em.getReference(articuloListNewArticuloToAttach.getClass(), articuloListNewArticuloToAttach.getId());
                attachedArticuloListNew.add(articuloListNewArticuloToAttach);
            }
            articuloListNew = attachedArticuloListNew;
            usuario.setArticuloList(articuloListNew);
            List<Articulo> attachedArticuloList1New = new ArrayList<Articulo>();
            for (Articulo articuloList1NewArticuloToAttach : articuloList1New) {
                articuloList1NewArticuloToAttach = em.getReference(articuloList1NewArticuloToAttach.getClass(), articuloList1NewArticuloToAttach.getId());
                attachedArticuloList1New.add(articuloList1NewArticuloToAttach);
            }
            articuloList1New = attachedArticuloList1New;
            usuario.setArticuloList1(articuloList1New);
            usuario = em.merge(usuario);
            if (correoIdOld != null && !correoIdOld.equals(correoIdNew)) {
                correoIdOld.getUsuarioList().remove(usuario);
                correoIdOld = em.merge(correoIdOld);
            }
            if (correoIdNew != null && !correoIdNew.equals(correoIdOld)) {
                correoIdNew.getUsuarioList().add(usuario);
                correoIdNew = em.merge(correoIdNew);
            }
            if (tarjetaIdOld != null && !tarjetaIdOld.equals(tarjetaIdNew)) {
                tarjetaIdOld.getUsuarioList().remove(usuario);
                tarjetaIdOld = em.merge(tarjetaIdOld);
            }
            if (tarjetaIdNew != null && !tarjetaIdNew.equals(tarjetaIdOld)) {
                tarjetaIdNew.getUsuarioList().add(usuario);
                tarjetaIdNew = em.merge(tarjetaIdNew);
            }
            for (Articulo articuloListOldArticulo : articuloListOld) {
                if (!articuloListNew.contains(articuloListOldArticulo)) {
                    articuloListOldArticulo.getUsuarioList().remove(usuario);
                    articuloListOldArticulo = em.merge(articuloListOldArticulo);
                }
            }
            for (Articulo articuloListNewArticulo : articuloListNew) {
                if (!articuloListOld.contains(articuloListNewArticulo)) {
                    articuloListNewArticulo.getUsuarioList().add(usuario);
                    articuloListNewArticulo = em.merge(articuloListNewArticulo);
                }
            }
            for (Articulo articuloList1OldArticulo : articuloList1Old) {
                if (!articuloList1New.contains(articuloList1OldArticulo)) {
                    articuloList1OldArticulo.getUsuarioList().remove(usuario);
                    articuloList1OldArticulo = em.merge(articuloList1OldArticulo);
                }
            }
            for (Articulo articuloList1NewArticulo : articuloList1New) {
                if (!articuloList1Old.contains(articuloList1NewArticulo)) {
                    articuloList1NewArticulo.getUsuarioList().add(usuario);
                    articuloList1NewArticulo = em.merge(articuloList1NewArticulo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Cuentacorreo correoId = usuario.getCorreoId();
            if (correoId != null) {
                correoId.getUsuarioList().remove(usuario);
                correoId = em.merge(correoId);
            }
            Tarjeta tarjetaId = usuario.getTarjetaId();
            if (tarjetaId != null) {
                tarjetaId.getUsuarioList().remove(usuario);
                tarjetaId = em.merge(tarjetaId);
            }
            List<Articulo> articuloList = usuario.getArticuloList();
            for (Articulo articuloListArticulo : articuloList) {
                articuloListArticulo.getUsuarioList().remove(usuario);
                articuloListArticulo = em.merge(articuloListArticulo);
            }
            List<Articulo> articuloList1 = usuario.getArticuloList1();
            for (Articulo articuloList1Articulo : articuloList1) {
                articuloList1Articulo.getUsuarioList().remove(usuario);
                articuloList1Articulo = em.merge(articuloList1Articulo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
