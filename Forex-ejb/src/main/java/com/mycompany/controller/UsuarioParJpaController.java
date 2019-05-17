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
import com.mycompany.entities.Usuario;
import com.mycompany.entities.Movimiento;
import com.mycompany.entities.UsuarioPar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author German
 */
public class UsuarioParJpaController implements Serializable {

    public UsuarioParJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPar usuarioPar) throws RollbackFailureException, Exception {
        if (usuarioPar.getMovimientoList() == null) {
            usuarioPar.setMovimientoList(new ArrayList<Movimiento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Par idPar = usuarioPar.getIdPar();
            if (idPar != null) {
                idPar = em.getReference(idPar.getClass(), idPar.getId());
                usuarioPar.setIdPar(idPar);
            }
            Usuario idUsuario = usuarioPar.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                usuarioPar.setIdUsuario(idUsuario);
            }
            List<Movimiento> attachedMovimientoList = new ArrayList<Movimiento>();
            for (Movimiento movimientoListMovimientoToAttach : usuarioPar.getMovimientoList()) {
                movimientoListMovimientoToAttach = em.getReference(movimientoListMovimientoToAttach.getClass(), movimientoListMovimientoToAttach.getId());
                attachedMovimientoList.add(movimientoListMovimientoToAttach);
            }
            usuarioPar.setMovimientoList(attachedMovimientoList);
            em.persist(usuarioPar);
            if (idPar != null) {
                idPar.getUsuarioParList().add(usuarioPar);
                idPar = em.merge(idPar);
            }
            if (idUsuario != null) {
                idUsuario.getUsuarioParList().add(usuarioPar);
                idUsuario = em.merge(idUsuario);
            }
            for (Movimiento movimientoListMovimiento : usuarioPar.getMovimientoList()) {
                UsuarioPar oldFkUsuarioParOfMovimientoListMovimiento = movimientoListMovimiento.getFkUsuarioPar();
                movimientoListMovimiento.setFkUsuarioPar(usuarioPar);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
                if (oldFkUsuarioParOfMovimientoListMovimiento != null) {
                    oldFkUsuarioParOfMovimientoListMovimiento.getMovimientoList().remove(movimientoListMovimiento);
                    oldFkUsuarioParOfMovimientoListMovimiento = em.merge(oldFkUsuarioParOfMovimientoListMovimiento);
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

    public void edit(UsuarioPar usuarioPar) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioPar persistentUsuarioPar = em.find(UsuarioPar.class, usuarioPar.getId());
            Par idParOld = persistentUsuarioPar.getIdPar();
            Par idParNew = usuarioPar.getIdPar();
            Usuario idUsuarioOld = persistentUsuarioPar.getIdUsuario();
            Usuario idUsuarioNew = usuarioPar.getIdUsuario();
            List<Movimiento> movimientoListOld = persistentUsuarioPar.getMovimientoList();
            List<Movimiento> movimientoListNew = usuarioPar.getMovimientoList();
            if (idParNew != null) {
                idParNew = em.getReference(idParNew.getClass(), idParNew.getId());
                usuarioPar.setIdPar(idParNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                usuarioPar.setIdUsuario(idUsuarioNew);
            }
            List<Movimiento> attachedMovimientoListNew = new ArrayList<Movimiento>();
            for (Movimiento movimientoListNewMovimientoToAttach : movimientoListNew) {
                movimientoListNewMovimientoToAttach = em.getReference(movimientoListNewMovimientoToAttach.getClass(), movimientoListNewMovimientoToAttach.getId());
                attachedMovimientoListNew.add(movimientoListNewMovimientoToAttach);
            }
            movimientoListNew = attachedMovimientoListNew;
            usuarioPar.setMovimientoList(movimientoListNew);
            usuarioPar = em.merge(usuarioPar);
            if (idParOld != null && !idParOld.equals(idParNew)) {
                idParOld.getUsuarioParList().remove(usuarioPar);
                idParOld = em.merge(idParOld);
            }
            if (idParNew != null && !idParNew.equals(idParOld)) {
                idParNew.getUsuarioParList().add(usuarioPar);
                idParNew = em.merge(idParNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getUsuarioParList().remove(usuarioPar);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getUsuarioParList().add(usuarioPar);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Movimiento movimientoListOldMovimiento : movimientoListOld) {
                if (!movimientoListNew.contains(movimientoListOldMovimiento)) {
                    movimientoListOldMovimiento.setFkUsuarioPar(null);
                    movimientoListOldMovimiento = em.merge(movimientoListOldMovimiento);
                }
            }
            for (Movimiento movimientoListNewMovimiento : movimientoListNew) {
                if (!movimientoListOld.contains(movimientoListNewMovimiento)) {
                    UsuarioPar oldFkUsuarioParOfMovimientoListNewMovimiento = movimientoListNewMovimiento.getFkUsuarioPar();
                    movimientoListNewMovimiento.setFkUsuarioPar(usuarioPar);
                    movimientoListNewMovimiento = em.merge(movimientoListNewMovimiento);
                    if (oldFkUsuarioParOfMovimientoListNewMovimiento != null && !oldFkUsuarioParOfMovimientoListNewMovimiento.equals(usuarioPar)) {
                        oldFkUsuarioParOfMovimientoListNewMovimiento.getMovimientoList().remove(movimientoListNewMovimiento);
                        oldFkUsuarioParOfMovimientoListNewMovimiento = em.merge(oldFkUsuarioParOfMovimientoListNewMovimiento);
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
                Integer id = usuarioPar.getId();
                if (findUsuarioPar(id) == null) {
                    throw new NonexistentEntityException("The usuarioPar with id " + id + " no longer exists.");
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
            UsuarioPar usuarioPar;
            try {
                usuarioPar = em.getReference(UsuarioPar.class, id);
                usuarioPar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPar with id " + id + " no longer exists.", enfe);
            }
            Par idPar = usuarioPar.getIdPar();
            if (idPar != null) {
                idPar.getUsuarioParList().remove(usuarioPar);
                idPar = em.merge(idPar);
            }
            Usuario idUsuario = usuarioPar.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getUsuarioParList().remove(usuarioPar);
                idUsuario = em.merge(idUsuario);
            }
            List<Movimiento> movimientoList = usuarioPar.getMovimientoList();
            for (Movimiento movimientoListMovimiento : movimientoList) {
                movimientoListMovimiento.setFkUsuarioPar(null);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
            }
            em.remove(usuarioPar);
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

    public List<UsuarioPar> findUsuarioParEntities() {
        return findUsuarioParEntities(true, -1, -1);
    }

    public List<UsuarioPar> findUsuarioParEntities(int maxResults, int firstResult) {
        return findUsuarioParEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPar> findUsuarioParEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPar.class));
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

    public UsuarioPar findUsuarioPar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPar.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioParCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPar> rt = cq.from(UsuarioPar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
