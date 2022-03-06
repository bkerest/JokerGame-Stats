/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jokergamestats.exceptions.NonexistentEntityException;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public class PrizecategoriesJpaController implements Serializable {

    public PrizecategoriesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prizecategories prizecategories) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Draws drawidFk = prizecategories.getDrawidFk();
            if (drawidFk != null) {
                drawidFk = em.getReference(drawidFk.getClass(), drawidFk.getDrawid());
                prizecategories.setDrawidFk(drawidFk);
            }
            em.persist(prizecategories);
            if (drawidFk != null) {
                drawidFk.getPrizecategoriesCollection().add(prizecategories);
                drawidFk = em.merge(drawidFk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prizecategories prizecategories) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prizecategories persistentPrizecategories = em.find(Prizecategories.class, prizecategories.getPid());
            Draws drawidFkOld = persistentPrizecategories.getDrawidFk();
            Draws drawidFkNew = prizecategories.getDrawidFk();
            if (drawidFkNew != null) {
                drawidFkNew = em.getReference(drawidFkNew.getClass(), drawidFkNew.getDrawid());
                prizecategories.setDrawidFk(drawidFkNew);
            }
            prizecategories = em.merge(prizecategories);
            if (drawidFkOld != null && !drawidFkOld.equals(drawidFkNew)) {
                drawidFkOld.getPrizecategoriesCollection().remove(prizecategories);
                drawidFkOld = em.merge(drawidFkOld);
            }
            if (drawidFkNew != null && !drawidFkNew.equals(drawidFkOld)) {
                drawidFkNew.getPrizecategoriesCollection().add(prizecategories);
                drawidFkNew = em.merge(drawidFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prizecategories.getPid();
                if (findPrizecategories(id) == null) {
                    throw new NonexistentEntityException("The prizecategories with id " + id + " no longer exists.");
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
            Prizecategories prizecategories;
            try {
                prizecategories = em.getReference(Prizecategories.class, id);
                prizecategories.getPid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prizecategories with id " + id + " no longer exists.", enfe);
            }
            Draws drawidFk = prizecategories.getDrawidFk();
            if (drawidFk != null) {
                drawidFk.getPrizecategoriesCollection().remove(prizecategories);
                drawidFk = em.merge(drawidFk);
            }
            em.remove(prizecategories);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prizecategories> findPrizecategoriesEntities() {
        return findPrizecategoriesEntities(true, -1, -1);
    }

    public List<Prizecategories> findPrizecategoriesEntities(int maxResults, int firstResult) {
        return findPrizecategoriesEntities(false, maxResults, firstResult);
    }

    private List<Prizecategories> findPrizecategoriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prizecategories.class));
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

    public Prizecategories findPrizecategories(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prizecategories.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrizecategoriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prizecategories> rt = cq.from(Prizecategories.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
