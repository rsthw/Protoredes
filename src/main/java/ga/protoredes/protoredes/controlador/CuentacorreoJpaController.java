/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.protoredes.protoredes.controlador;

import ga.protoredes.protoredes.controlador.exceptions.IllegalOrphanException;
import ga.protoredes.protoredes.controlador.exceptions.NonexistentEntityException;
import ga.protoredes.protoredes.modelo.Cuentacorreo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ga.protoredes.protoredes.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author antonio
 */
public class CuentacorreoJpaController implements Serializable {

    public CuentacorreoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuentacorreo cuentacorreo) {
        if (cuentacorreo.getUsuarioList() == null) {
            cuentacorreo.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : cuentacorreo.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            cuentacorreo.setUsuarioList(attachedUsuarioList);
            em.persist(cuentacorreo);
            for (Usuario usuarioListUsuario : cuentacorreo.getUsuarioList()) {
                Cuentacorreo oldCorreoIdOfUsuarioListUsuario = usuarioListUsuario.getCorreoId();
                usuarioListUsuario.setCorreoId(cuentacorreo);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldCorreoIdOfUsuarioListUsuario != null) {
                    oldCorreoIdOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldCorreoIdOfUsuarioListUsuario = em.merge(oldCorreoIdOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuentacorreo cuentacorreo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuentacorreo persistentCuentacorreo = em.find(Cuentacorreo.class, cuentacorreo.getId());
            List<Usuario> usuarioListOld = persistentCuentacorreo.getUsuarioList();
            List<Usuario> usuarioListNew = cuentacorreo.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its correoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            cuentacorreo.setUsuarioList(usuarioListNew);
            cuentacorreo = em.merge(cuentacorreo);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Cuentacorreo oldCorreoIdOfUsuarioListNewUsuario = usuarioListNewUsuario.getCorreoId();
                    usuarioListNewUsuario.setCorreoId(cuentacorreo);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldCorreoIdOfUsuarioListNewUsuario != null && !oldCorreoIdOfUsuarioListNewUsuario.equals(cuentacorreo)) {
                        oldCorreoIdOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldCorreoIdOfUsuarioListNewUsuario = em.merge(oldCorreoIdOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentacorreo.getId();
                if (findCuentacorreo(id) == null) {
                    throw new NonexistentEntityException("The cuentacorreo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuentacorreo cuentacorreo;
            try {
                cuentacorreo = em.getReference(Cuentacorreo.class, id);
                cuentacorreo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentacorreo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = cuentacorreo.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuentacorreo (" + cuentacorreo + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable correoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cuentacorreo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuentacorreo> findCuentacorreoEntities() {
        return findCuentacorreoEntities(true, -1, -1);
    }

    public List<Cuentacorreo> findCuentacorreoEntities(int maxResults, int firstResult) {
        return findCuentacorreoEntities(false, maxResults, firstResult);
    }

    private List<Cuentacorreo> findCuentacorreoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuentacorreo.class));
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

    public Cuentacorreo findCuentacorreo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuentacorreo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentacorreoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuentacorreo> rt = cq.from(Cuentacorreo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Cuentacorreo findCuentaByCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Cuentacorreo> consultaCuentacorreos= em.createNamedQuery("Cuentacorreo.findByCorreo", Cuentacorreo.class);
        consultaCuentacorreos.setParameter("correo",correo );
        List<Cuentacorreo> lista= consultaCuentacorreos.getResultList();
        return lista.get(0);
    }
  }
    

