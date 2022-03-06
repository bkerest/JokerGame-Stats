/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jokergamestats.exceptions.IllegalOrphanException;
import jokergamestats.exceptions.NonexistentEntityException;
import jokergamestats.exceptions.PreexistingEntityException;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public class DrawsJpaController implements Serializable {

    public DrawsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Draws draws) throws PreexistingEntityException, Exception {
        if (draws.getPrizecategoriesCollection() == null) {
            draws.setPrizecategoriesCollection(new ArrayList<Prizecategories>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Prizecategories> attachedPrizecategoriesCollection = new ArrayList<Prizecategories>();
            for (Prizecategories prizecategoriesCollectionPrizecategoriesToAttach : draws.getPrizecategoriesCollection()) {
                prizecategoriesCollectionPrizecategoriesToAttach = em.getReference(prizecategoriesCollectionPrizecategoriesToAttach.getClass(), prizecategoriesCollectionPrizecategoriesToAttach.getPid());
                attachedPrizecategoriesCollection.add(prizecategoriesCollectionPrizecategoriesToAttach);
            }
            draws.setPrizecategoriesCollection(attachedPrizecategoriesCollection);
            em.persist(draws);
            for (Prizecategories prizecategoriesCollectionPrizecategories : draws.getPrizecategoriesCollection()) {
                Draws oldDrawidFkOfPrizecategoriesCollectionPrizecategories = prizecategoriesCollectionPrizecategories.getDrawidFk();
                prizecategoriesCollectionPrizecategories.setDrawidFk(draws);
                prizecategoriesCollectionPrizecategories = em.merge(prizecategoriesCollectionPrizecategories);
                if (oldDrawidFkOfPrizecategoriesCollectionPrizecategories != null) {
                    oldDrawidFkOfPrizecategoriesCollectionPrizecategories.getPrizecategoriesCollection().remove(prizecategoriesCollectionPrizecategories);
                    oldDrawidFkOfPrizecategoriesCollectionPrizecategories = em.merge(oldDrawidFkOfPrizecategoriesCollectionPrizecategories);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDraws(draws.getDrawid()) != null) {
                throw new PreexistingEntityException("Draws " + draws + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Draws draws) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Draws persistentDraws = em.find(Draws.class, draws.getDrawid());
            Collection<Prizecategories> prizecategoriesCollectionOld = persistentDraws.getPrizecategoriesCollection();
            Collection<Prizecategories> prizecategoriesCollectionNew = draws.getPrizecategoriesCollection();
            List<String> illegalOrphanMessages = null;
            for (Prizecategories prizecategoriesCollectionOldPrizecategories : prizecategoriesCollectionOld) {
                if (!prizecategoriesCollectionNew.contains(prizecategoriesCollectionOldPrizecategories)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prizecategories " + prizecategoriesCollectionOldPrizecategories + " since its drawidFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Prizecategories> attachedPrizecategoriesCollectionNew = new ArrayList<Prizecategories>();
            for (Prizecategories prizecategoriesCollectionNewPrizecategoriesToAttach : prizecategoriesCollectionNew) {
                prizecategoriesCollectionNewPrizecategoriesToAttach = em.getReference(prizecategoriesCollectionNewPrizecategoriesToAttach.getClass(), prizecategoriesCollectionNewPrizecategoriesToAttach.getPid());
                attachedPrizecategoriesCollectionNew.add(prizecategoriesCollectionNewPrizecategoriesToAttach);
            }
            prizecategoriesCollectionNew = attachedPrizecategoriesCollectionNew;
            draws.setPrizecategoriesCollection(prizecategoriesCollectionNew);
            draws = em.merge(draws);
            for (Prizecategories prizecategoriesCollectionNewPrizecategories : prizecategoriesCollectionNew) {
                if (!prizecategoriesCollectionOld.contains(prizecategoriesCollectionNewPrizecategories)) {
                    Draws oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories = prizecategoriesCollectionNewPrizecategories.getDrawidFk();
                    prizecategoriesCollectionNewPrizecategories.setDrawidFk(draws);
                    prizecategoriesCollectionNewPrizecategories = em.merge(prizecategoriesCollectionNewPrizecategories);
                    if (oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories != null && !oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories.equals(draws)) {
                        oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories.getPrizecategoriesCollection().remove(prizecategoriesCollectionNewPrizecategories);
                        oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories = em.merge(oldDrawidFkOfPrizecategoriesCollectionNewPrizecategories);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = draws.getDrawid();
                if (findDraws(id) == null) {
                    throw new NonexistentEntityException("The draws with id " + id + " no longer exists.");
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
            Draws draws;
            try {
                draws = em.getReference(Draws.class, id);
                draws.getDrawid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The draws with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Prizecategories> prizecategoriesCollectionOrphanCheck = draws.getPrizecategoriesCollection();
            for (Prizecategories prizecategoriesCollectionOrphanCheckPrizecategories : prizecategoriesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Draws (" + draws + ") cannot be destroyed since the Prizecategories " + prizecategoriesCollectionOrphanCheckPrizecategories + " in its prizecategoriesCollection field has a non-nullable drawidFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(draws);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Draws> findDrawsEntities() {
        return findDrawsEntities(true, -1, -1);
    }

    public List<Draws> findDrawsEntities(int maxResults, int firstResult) {
        return findDrawsEntities(false, maxResults, firstResult);
    }

    private List<Draws> findDrawsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Draws.class));
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

    public Draws findDraws(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Draws.class, id);
        } finally {
            em.close();
        }
    }

    public int getDrawsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Draws> rt = cq.from(Draws.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
