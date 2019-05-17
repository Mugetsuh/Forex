/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.controller.exceptions.NonexistentEntityException;
import com.mycompany.controller.exceptions.RollbackFailureException;
import com.mycompany.entities.Par;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entities.UsuarioPar;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.entities.ValorPar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class ParJpaController implements Serializable {

    public ParJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Par par) throws RollbackFailureException, Exception {
        if (par.getUsuarioParList() == null) {
            par.setUsuarioParList(new ArrayList<UsuarioPar>());
        }
        if (par.getValorParList() == null) {
            par.setValorParList(new ArrayList<ValorPar>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<UsuarioPar> attachedUsuarioParList = new ArrayList<UsuarioPar>();
            for (UsuarioPar usuarioParListUsuarioParToAttach : par.getUsuarioParList()) {
                usuarioParListUsuarioParToAttach = em.getReference(usuarioParListUsuarioParToAttach.getClass(), usuarioParListUsuarioParToAttach.getId());
                attachedUsuarioParList.add(usuarioParListUsuarioParToAttach);
            }
            par.setUsuarioParList(attachedUsuarioParList);
            List<ValorPar> attachedValorParList = new ArrayList<ValorPar>();
            for (ValorPar valorParListValorParToAttach : par.getValorParList()) {
                valorParListValorParToAttach = em.getReference(valorParListValorParToAttach.getClass(), valorParListValorParToAttach.getId());
                attachedValorParList.add(valorParListValorParToAttach);
            }
            par.setValorParList(attachedValorParList);
            em.persist(par);
            for (UsuarioPar usuarioParListUsuarioPar : par.getUsuarioParList()) {
                Par oldIdParOfUsuarioParListUsuarioPar = usuarioParListUsuarioPar.getIdPar();
                usuarioParListUsuarioPar.setIdPar(par);
                usuarioParListUsuarioPar = em.merge(usuarioParListUsuarioPar);
                if (oldIdParOfUsuarioParListUsuarioPar != null) {
                    oldIdParOfUsuarioParListUsuarioPar.getUsuarioParList().remove(usuarioParListUsuarioPar);
                    oldIdParOfUsuarioParListUsuarioPar = em.merge(oldIdParOfUsuarioParListUsuarioPar);
                }
            }
            for (ValorPar valorParListValorPar : par.getValorParList()) {
                Par oldPkParOfValorParListValorPar = valorParListValorPar.getPkPar();
                valorParListValorPar.setPkPar(par);
                valorParListValorPar = em.merge(valorParListValorPar);
                if (oldPkParOfValorParListValorPar != null) {
                    oldPkParOfValorParListValorPar.getValorParList().remove(valorParListValorPar);
                    oldPkParOfValorParListValorPar = em.merge(oldPkParOfValorParListValorPar);
                }
            }
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

    public void edit(Par par) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Par persistentPar = em.find(Par.class, par.getId());
            List<UsuarioPar> usuarioParListOld = persistentPar.getUsuarioParList();
            List<UsuarioPar> usuarioParListNew = par.getUsuarioParList();
            List<ValorPar> valorParListOld = persistentPar.getValorParList();
            List<ValorPar> valorParListNew = par.getValorParList();
            List<UsuarioPar> attachedUsuarioParListNew = new ArrayList<UsuarioPar>();
            for (UsuarioPar usuarioParListNewUsuarioParToAttach : usuarioParListNew) {
                usuarioParListNewUsuarioParToAttach = em.getReference(usuarioParListNewUsuarioParToAttach.getClass(), usuarioParListNewUsuarioParToAttach.getId());
                attachedUsuarioParListNew.add(usuarioParListNewUsuarioParToAttach);
            }
            usuarioParListNew = attachedUsuarioParListNew;
            par.setUsuarioParList(usuarioParListNew);
            List<ValorPar> attachedValorParListNew = new ArrayList<ValorPar>();
            for (ValorPar valorParListNewValorParToAttach : valorParListNew) {
                valorParListNewValorParToAttach = em.getReference(valorParListNewValorParToAttach.getClass(), valorParListNewValorParToAttach.getId());
                attachedValorParListNew.add(valorParListNewValorParToAttach);
            }
            valorParListNew = attachedValorParListNew;
            par.setValorParList(valorParListNew);
            par = em.merge(par);
            for (UsuarioPar usuarioParListOldUsuarioPar : usuarioParListOld) {
                if (!usuarioParListNew.contains(usuarioParListOldUsuarioPar)) {
                    usuarioParListOldUsuarioPar.setIdPar(null);
                    usuarioParListOldUsuarioPar = em.merge(usuarioParListOldUsuarioPar);
                }
            }
            for (UsuarioPar usuarioParListNewUsuarioPar : usuarioParListNew) {
                if (!usuarioParListOld.contains(usuarioParListNewUsuarioPar)) {
                    Par oldIdParOfUsuarioParListNewUsuarioPar = usuarioParListNewUsuarioPar.getIdPar();
                    usuarioParListNewUsuarioPar.setIdPar(par);
                    usuarioParListNewUsuarioPar = em.merge(usuarioParListNewUsuarioPar);
                    if (oldIdParOfUsuarioParListNewUsuarioPar != null && !oldIdParOfUsuarioParListNewUsuarioPar.equals(par)) {
                        oldIdParOfUsuarioParListNewUsuarioPar.getUsuarioParList().remove(usuarioParListNewUsuarioPar);
                        oldIdParOfUsuarioParListNewUsuarioPar = em.merge(oldIdParOfUsuarioParListNewUsuarioPar);
                    }
                }
            }
            for (ValorPar valorParListOldValorPar : valorParListOld) {
                if (!valorParListNew.contains(valorParListOldValorPar)) {
                    valorParListOldValorPar.setPkPar(null);
                    valorParListOldValorPar = em.merge(valorParListOldValorPar);
                }
            }
            for (ValorPar valorParListNewValorPar : valorParListNew) {
                if (!valorParListOld.contains(valorParListNewValorPar)) {
                    Par oldPkParOfValorParListNewValorPar = valorParListNewValorPar.getPkPar();
                    valorParListNewValorPar.setPkPar(par);
                    valorParListNewValorPar = em.merge(valorParListNewValorPar);
                    if (oldPkParOfValorParListNewValorPar != null && !oldPkParOfValorParListNewValorPar.equals(par)) {
                        oldPkParOfValorParListNewValorPar.getValorParList().remove(valorParListNewValorPar);
                        oldPkParOfValorParListNewValorPar = em.merge(oldPkParOfValorParListNewValorPar);
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
                Integer id = par.getId();
                if (findPar(id) == null) {
                    throw new NonexistentEntityException("The par with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Par par;
            try {
                par = em.getReference(Par.class, id);
                par.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The par with id " + id + " no longer exists.", enfe);
            }
            List<UsuarioPar> usuarioParList = par.getUsuarioParList();
            for (UsuarioPar usuarioParListUsuarioPar : usuarioParList) {
                usuarioParListUsuarioPar.setIdPar(null);
                usuarioParListUsuarioPar = em.merge(usuarioParListUsuarioPar);
            }
            List<ValorPar> valorParList = par.getValorParList();
            for (ValorPar valorParListValorPar : valorParList) {
                valorParListValorPar.setPkPar(null);
                valorParListValorPar = em.merge(valorParListValorPar);
            }
            em.remove(par);
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

    public List<Par> findParEntities() {
        return findParEntities(true, -1, -1);
    }

    public List<Par> findParEntities(int maxResults, int firstResult) {
        return findParEntities(false, maxResults, firstResult);
    }

    private List<Par> findParEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Par.class));
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

    public Par findPar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Par.class, id);
        } finally {
            em.close();
        }
    }

    public int getParCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Par> rt = cq.from(Par.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
