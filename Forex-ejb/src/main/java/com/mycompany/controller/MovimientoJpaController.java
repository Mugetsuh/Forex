/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.controller.exceptions.NonexistentEntityException;
import com.mycompany.controller.exceptions.RollbackFailureException;
import com.mycompany.entities.Movimiento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entities.Saldo;
import com.mycompany.entities.UsuarioPar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class MovimientoJpaController implements Serializable {

    public MovimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimiento movimiento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Saldo fkSaldo = movimiento.getFkSaldo();
            if (fkSaldo != null) {
                fkSaldo = em.getReference(fkSaldo.getClass(), fkSaldo.getId());
                movimiento.setFkSaldo(fkSaldo);
            }
            UsuarioPar fkUsuarioPar = movimiento.getFkUsuarioPar();
            if (fkUsuarioPar != null) {
                fkUsuarioPar = em.getReference(fkUsuarioPar.getClass(), fkUsuarioPar.getId());
                movimiento.setFkUsuarioPar(fkUsuarioPar);
            }
            em.persist(movimiento);
            if (fkSaldo != null) {
                fkSaldo.getMovimientoList().add(movimiento);
                fkSaldo = em.merge(fkSaldo);
            }
            if (fkUsuarioPar != null) {
                fkUsuarioPar.getMovimientoList().add(movimiento);
                fkUsuarioPar = em.merge(fkUsuarioPar);
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

    public void edit(Movimiento movimiento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Movimiento persistentMovimiento = em.find(Movimiento.class, movimiento.getId());
            Saldo fkSaldoOld = persistentMovimiento.getFkSaldo();
            Saldo fkSaldoNew = movimiento.getFkSaldo();
            UsuarioPar fkUsuarioParOld = persistentMovimiento.getFkUsuarioPar();
            UsuarioPar fkUsuarioParNew = movimiento.getFkUsuarioPar();
            if (fkSaldoNew != null) {
                fkSaldoNew = em.getReference(fkSaldoNew.getClass(), fkSaldoNew.getId());
                movimiento.setFkSaldo(fkSaldoNew);
            }
            if (fkUsuarioParNew != null) {
                fkUsuarioParNew = em.getReference(fkUsuarioParNew.getClass(), fkUsuarioParNew.getId());
                movimiento.setFkUsuarioPar(fkUsuarioParNew);
            }
            movimiento = em.merge(movimiento);
            if (fkSaldoOld != null && !fkSaldoOld.equals(fkSaldoNew)) {
                fkSaldoOld.getMovimientoList().remove(movimiento);
                fkSaldoOld = em.merge(fkSaldoOld);
            }
            if (fkSaldoNew != null && !fkSaldoNew.equals(fkSaldoOld)) {
                fkSaldoNew.getMovimientoList().add(movimiento);
                fkSaldoNew = em.merge(fkSaldoNew);
            }
            if (fkUsuarioParOld != null && !fkUsuarioParOld.equals(fkUsuarioParNew)) {
                fkUsuarioParOld.getMovimientoList().remove(movimiento);
                fkUsuarioParOld = em.merge(fkUsuarioParOld);
            }
            if (fkUsuarioParNew != null && !fkUsuarioParNew.equals(fkUsuarioParOld)) {
                fkUsuarioParNew.getMovimientoList().add(movimiento);
                fkUsuarioParNew = em.merge(fkUsuarioParNew);
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
                Integer id = movimiento.getId();
                if (findMovimiento(id) == null) {
                    throw new NonexistentEntityException("The movimiento with id " + id + " no longer exists.");
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
            Movimiento movimiento;
            try {
                movimiento = em.getReference(Movimiento.class, id);
                movimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimiento with id " + id + " no longer exists.", enfe);
            }
            Saldo fkSaldo = movimiento.getFkSaldo();
            if (fkSaldo != null) {
                fkSaldo.getMovimientoList().remove(movimiento);
                fkSaldo = em.merge(fkSaldo);
            }
            UsuarioPar fkUsuarioPar = movimiento.getFkUsuarioPar();
            if (fkUsuarioPar != null) {
                fkUsuarioPar.getMovimientoList().remove(movimiento);
                fkUsuarioPar = em.merge(fkUsuarioPar);
            }
            em.remove(movimiento);
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

    public List<Movimiento> findMovimientoEntities() {
        return findMovimientoEntities(true, -1, -1);
    }

    public List<Movimiento> findMovimientoEntities(int maxResults, int firstResult) {
        return findMovimientoEntities(false, maxResults, firstResult);
    }

    private List<Movimiento> findMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimiento.class));
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

    public Movimiento findMovimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimiento> rt = cq.from(Movimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
