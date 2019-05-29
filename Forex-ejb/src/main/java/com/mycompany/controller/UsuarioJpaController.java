/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.controller.exceptions.IllegalOrphanException;
import com.mycompany.controller.exceptions.NonexistentEntityException;
import com.mycompany.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entities.Saldo;
import com.mycompany.entities.Usuario;
import com.mycompany.entities.UsuarioPar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_Forex-ejb_ejb_1.0-SNAPSHOTPU").createEntityManager();
    }
    private UserTransaction utx = null;
    private EntityManager emf = null;

    public EntityManager getEntityManager() {
        return emf;
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        try {
            emf.getTransaction().begin();
            emf.persist(usuario);
            emf.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Saldo saldoOld = persistentUsuario.getSaldo();
            Saldo saldoNew = usuario.getSaldo();
            List<UsuarioPar> usuarioParListOld = persistentUsuario.getUsuarioParList();
            List<UsuarioPar> usuarioParListNew = usuario.getUsuarioParList();
            List<String> illegalOrphanMessages = null;
            if (saldoOld != null && !saldoOld.equals(saldoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Saldo " + saldoOld + " since its usuario field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (saldoNew != null) {
                saldoNew = em.getReference(saldoNew.getClass(), saldoNew.getId());
                usuario.setSaldo(saldoNew);
            }
            List<UsuarioPar> attachedUsuarioParListNew = new ArrayList<UsuarioPar>();
            for (UsuarioPar usuarioParListNewUsuarioParToAttach : usuarioParListNew) {
                usuarioParListNewUsuarioParToAttach = em.getReference(usuarioParListNewUsuarioParToAttach.getClass(), usuarioParListNewUsuarioParToAttach.getId());
                attachedUsuarioParListNew.add(usuarioParListNewUsuarioParToAttach);
            }
            usuarioParListNew = attachedUsuarioParListNew;
            usuario.setUsuarioParList(usuarioParListNew);
            usuario = em.merge(usuario);
            if (saldoNew != null && !saldoNew.equals(saldoOld)) {
                Usuario oldUsuarioOfSaldo = saldoNew.getUsuario();
                if (oldUsuarioOfSaldo != null) {
                    oldUsuarioOfSaldo.setSaldo(null);
                    oldUsuarioOfSaldo = em.merge(oldUsuarioOfSaldo);
                }
                saldoNew.setUsuario(usuario);
                saldoNew = em.merge(saldoNew);
            }
            for (UsuarioPar usuarioParListOldUsuarioPar : usuarioParListOld) {
                if (!usuarioParListNew.contains(usuarioParListOldUsuarioPar)) {
                    usuarioParListOldUsuarioPar.setIdUsuario(null);
                    usuarioParListOldUsuarioPar = em.merge(usuarioParListOldUsuarioPar);
                }
            }
            for (UsuarioPar usuarioParListNewUsuarioPar : usuarioParListNew) {
                if (!usuarioParListOld.contains(usuarioParListNewUsuarioPar)) {
                    Usuario oldIdUsuarioOfUsuarioParListNewUsuarioPar = usuarioParListNewUsuarioPar.getIdUsuario();
                    usuarioParListNewUsuarioPar.setIdUsuario(usuario);
                    usuarioParListNewUsuarioPar = em.merge(usuarioParListNewUsuarioPar);
                    if (oldIdUsuarioOfUsuarioParListNewUsuarioPar != null && !oldIdUsuarioOfUsuarioParListNewUsuarioPar.equals(usuario)) {
                        oldIdUsuarioOfUsuarioParListNewUsuarioPar.getUsuarioParList().remove(usuarioParListNewUsuarioPar);
                        oldIdUsuarioOfUsuarioParListNewUsuarioPar = em.merge(oldIdUsuarioOfUsuarioParListNewUsuarioPar);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Saldo saldoOrphanCheck = usuario.getSaldo();
            if (saldoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Saldo " + saldoOrphanCheck + " in its saldo field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UsuarioPar> usuarioParList = usuario.getUsuarioParList();
            for (UsuarioPar usuarioParListUsuarioPar : usuarioParList) {
                usuarioParListUsuarioPar.setIdUsuario(null);
                usuarioParListUsuarioPar = em.merge(usuarioParListUsuarioPar);
            }
            em.remove(usuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
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
