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
import com.mycompany.entities.Usuario;
import com.mycompany.entities.Movimiento;
import com.mycompany.entities.Saldo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class SaldoJpaController implements Serializable {

    public SaldoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Saldo saldo) throws IllegalOrphanException, RollbackFailureException, Exception {
        if (saldo.getMovimientoList() == null) {
            saldo.setMovimientoList(new ArrayList<Movimiento>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = saldo.getUsuario();
        if (usuarioOrphanCheck != null) {
            Saldo oldSaldoOfUsuario = usuarioOrphanCheck.getSaldo();
            if (oldSaldoOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type Saldo whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario = saldo.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                saldo.setUsuario(usuario);
            }
            List<Movimiento> attachedMovimientoList = new ArrayList<Movimiento>();
            for (Movimiento movimientoListMovimientoToAttach : saldo.getMovimientoList()) {
                movimientoListMovimientoToAttach = em.getReference(movimientoListMovimientoToAttach.getClass(), movimientoListMovimientoToAttach.getId());
                attachedMovimientoList.add(movimientoListMovimientoToAttach);
            }
            saldo.setMovimientoList(attachedMovimientoList);
            em.persist(saldo);
            if (usuario != null) {
                usuario.setSaldo(saldo);
                usuario = em.merge(usuario);
            }
            for (Movimiento movimientoListMovimiento : saldo.getMovimientoList()) {
                Saldo oldFkSaldoOfMovimientoListMovimiento = movimientoListMovimiento.getFkSaldo();
                movimientoListMovimiento.setFkSaldo(saldo);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
                if (oldFkSaldoOfMovimientoListMovimiento != null) {
                    oldFkSaldoOfMovimientoListMovimiento.getMovimientoList().remove(movimientoListMovimiento);
                    oldFkSaldoOfMovimientoListMovimiento = em.merge(oldFkSaldoOfMovimientoListMovimiento);
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

    public void edit(Saldo saldo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Saldo persistentSaldo = em.find(Saldo.class, saldo.getId());
            Usuario usuarioOld = persistentSaldo.getUsuario();
            Usuario usuarioNew = saldo.getUsuario();
            List<Movimiento> movimientoListOld = persistentSaldo.getMovimientoList();
            List<Movimiento> movimientoListNew = saldo.getMovimientoList();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Saldo oldSaldoOfUsuario = usuarioNew.getSaldo();
                if (oldSaldoOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type Saldo whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                saldo.setUsuario(usuarioNew);
            }
            List<Movimiento> attachedMovimientoListNew = new ArrayList<Movimiento>();
            for (Movimiento movimientoListNewMovimientoToAttach : movimientoListNew) {
                movimientoListNewMovimientoToAttach = em.getReference(movimientoListNewMovimientoToAttach.getClass(), movimientoListNewMovimientoToAttach.getId());
                attachedMovimientoListNew.add(movimientoListNewMovimientoToAttach);
            }
            movimientoListNew = attachedMovimientoListNew;
            saldo.setMovimientoList(movimientoListNew);
            saldo = em.merge(saldo);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setSaldo(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setSaldo(saldo);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Movimiento movimientoListOldMovimiento : movimientoListOld) {
                if (!movimientoListNew.contains(movimientoListOldMovimiento)) {
                    movimientoListOldMovimiento.setFkSaldo(null);
                    movimientoListOldMovimiento = em.merge(movimientoListOldMovimiento);
                }
            }
            for (Movimiento movimientoListNewMovimiento : movimientoListNew) {
                if (!movimientoListOld.contains(movimientoListNewMovimiento)) {
                    Saldo oldFkSaldoOfMovimientoListNewMovimiento = movimientoListNewMovimiento.getFkSaldo();
                    movimientoListNewMovimiento.setFkSaldo(saldo);
                    movimientoListNewMovimiento = em.merge(movimientoListNewMovimiento);
                    if (oldFkSaldoOfMovimientoListNewMovimiento != null && !oldFkSaldoOfMovimientoListNewMovimiento.equals(saldo)) {
                        oldFkSaldoOfMovimientoListNewMovimiento.getMovimientoList().remove(movimientoListNewMovimiento);
                        oldFkSaldoOfMovimientoListNewMovimiento = em.merge(oldFkSaldoOfMovimientoListNewMovimiento);
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
                Integer id = saldo.getId();
                if (findSaldo(id) == null) {
                    throw new NonexistentEntityException("The saldo with id " + id + " no longer exists.");
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
            Saldo saldo;
            try {
                saldo = em.getReference(Saldo.class, id);
                saldo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saldo with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = saldo.getUsuario();
            if (usuario != null) {
                usuario.setSaldo(null);
                usuario = em.merge(usuario);
            }
            List<Movimiento> movimientoList = saldo.getMovimientoList();
            for (Movimiento movimientoListMovimiento : movimientoList) {
                movimientoListMovimiento.setFkSaldo(null);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
            }
            em.remove(saldo);
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

    public List<Saldo> findSaldoEntities() {
        return findSaldoEntities(true, -1, -1);
    }

    public List<Saldo> findSaldoEntities(int maxResults, int firstResult) {
        return findSaldoEntities(false, maxResults, firstResult);
    }

    private List<Saldo> findSaldoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Saldo.class));
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

    public Saldo findSaldo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Saldo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaldoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Saldo> rt = cq.from(Saldo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
