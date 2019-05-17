/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.controller.exceptions.NonexistentEntityException;
import com.mycompany.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entities.Par;
import com.mycompany.entities.ValorPar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class ValorParJpaController implements Serializable {

    public ValorParJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorPar valorPar) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Par pkPar = valorPar.getPkPar();
            if (pkPar != null) {
                pkPar = em.getReference(pkPar.getClass(), pkPar.getId());
                valorPar.setPkPar(pkPar);
            }
            em.persist(valorPar);
            if (pkPar != null) {
                pkPar.getValorParList().add(valorPar);
                pkPar = em.merge(pkPar);
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

    public void edit(ValorPar valorPar) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ValorPar persistentValorPar = em.find(ValorPar.class, valorPar.getId());
            Par pkParOld = persistentValorPar.getPkPar();
            Par pkParNew = valorPar.getPkPar();
            if (pkParNew != null) {
                pkParNew = em.getReference(pkParNew.getClass(), pkParNew.getId());
                valorPar.setPkPar(pkParNew);
            }
            valorPar = em.merge(valorPar);
            if (pkParOld != null && !pkParOld.equals(pkParNew)) {
                pkParOld.getValorParList().remove(valorPar);
                pkParOld = em.merge(pkParOld);
            }
            if (pkParNew != null && !pkParNew.equals(pkParOld)) {
                pkParNew.getValorParList().add(valorPar);
                pkParNew = em.merge(pkParNew);
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
                Integer id = valorPar.getId();
                if (findValorPar(id) == null) {
                    throw new NonexistentEntityException("The valorPar with id " + id + " no longer exists.");
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
            ValorPar valorPar;
            try {
                valorPar = em.getReference(ValorPar.class, id);
                valorPar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorPar with id " + id + " no longer exists.", enfe);
            }
            Par pkPar = valorPar.getPkPar();
            if (pkPar != null) {
                pkPar.getValorParList().remove(valorPar);
                pkPar = em.merge(pkPar);
            }
            em.remove(valorPar);
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

    public List<ValorPar> findValorParEntities() {
        return findValorParEntities(true, -1, -1);
    }

    public List<ValorPar> findValorParEntities(int maxResults, int firstResult) {
        return findValorParEntities(false, maxResults, firstResult);
    }

    private List<ValorPar> findValorParEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorPar.class));
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

    public ValorPar findValorPar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorPar.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorParCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorPar> rt = cq.from(ValorPar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
