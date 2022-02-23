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
import jokergamestats.exceptions.NonexistentEntityException;
import jokergamestats.exceptions.PreexistingEntityException;

/**
 *
 * @author gkiop
 */
public class GamesJpaController implements Serializable {

    public GamesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Games games) throws PreexistingEntityException, Exception {
        if (games.getDrawsCollection() == null) {
            games.setDrawsCollection(new ArrayList<Draws>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Draws> attachedDrawsCollection = new ArrayList<Draws>();
            for (Draws drawsCollectionDrawsToAttach : games.getDrawsCollection()) {
                drawsCollectionDrawsToAttach = em.getReference(drawsCollectionDrawsToAttach.getClass(), drawsCollectionDrawsToAttach.getDrawid());
                attachedDrawsCollection.add(drawsCollectionDrawsToAttach);
            }
            games.setDrawsCollection(attachedDrawsCollection);
            em.persist(games);
            for (Draws drawsCollectionDraws : games.getDrawsCollection()) {
                Games oldGameidOfDrawsCollectionDraws = drawsCollectionDraws.getGameid();
                drawsCollectionDraws.setGameid(games);
                drawsCollectionDraws = em.merge(drawsCollectionDraws);
                if (oldGameidOfDrawsCollectionDraws != null) {
                    oldGameidOfDrawsCollectionDraws.getDrawsCollection().remove(drawsCollectionDraws);
                    oldGameidOfDrawsCollectionDraws = em.merge(oldGameidOfDrawsCollectionDraws);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGames(games.getGameid()) != null) {
                throw new PreexistingEntityException("Games " + games + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Games games) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Games persistentGames = em.find(Games.class, games.getGameid());
            Collection<Draws> drawsCollectionOld = persistentGames.getDrawsCollection();
            Collection<Draws> drawsCollectionNew = games.getDrawsCollection();
            Collection<Draws> attachedDrawsCollectionNew = new ArrayList<Draws>();
            for (Draws drawsCollectionNewDrawsToAttach : drawsCollectionNew) {
                drawsCollectionNewDrawsToAttach = em.getReference(drawsCollectionNewDrawsToAttach.getClass(), drawsCollectionNewDrawsToAttach.getDrawid());
                attachedDrawsCollectionNew.add(drawsCollectionNewDrawsToAttach);
            }
            drawsCollectionNew = attachedDrawsCollectionNew;
            games.setDrawsCollection(drawsCollectionNew);
            games = em.merge(games);
            for (Draws drawsCollectionOldDraws : drawsCollectionOld) {
                if (!drawsCollectionNew.contains(drawsCollectionOldDraws)) {
                    drawsCollectionOldDraws.setGameid(null);
                    drawsCollectionOldDraws = em.merge(drawsCollectionOldDraws);
                }
            }
            for (Draws drawsCollectionNewDraws : drawsCollectionNew) {
                if (!drawsCollectionOld.contains(drawsCollectionNewDraws)) {
                    Games oldGameidOfDrawsCollectionNewDraws = drawsCollectionNewDraws.getGameid();
                    drawsCollectionNewDraws.setGameid(games);
                    drawsCollectionNewDraws = em.merge(drawsCollectionNewDraws);
                    if (oldGameidOfDrawsCollectionNewDraws != null && !oldGameidOfDrawsCollectionNewDraws.equals(games)) {
                        oldGameidOfDrawsCollectionNewDraws.getDrawsCollection().remove(drawsCollectionNewDraws);
                        oldGameidOfDrawsCollectionNewDraws = em.merge(oldGameidOfDrawsCollectionNewDraws);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = games.getGameid();
                if (findGames(id) == null) {
                    throw new NonexistentEntityException("The games with id " + id + " no longer exists.");
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
            Games games;
            try {
                games = em.getReference(Games.class, id);
                games.getGameid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The games with id " + id + " no longer exists.", enfe);
            }
            Collection<Draws> drawsCollection = games.getDrawsCollection();
            for (Draws drawsCollectionDraws : drawsCollection) {
                drawsCollectionDraws.setGameid(null);
                drawsCollectionDraws = em.merge(drawsCollectionDraws);
            }
            em.remove(games);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Games> findGamesEntities() {
        return findGamesEntities(true, -1, -1);
    }

    public List<Games> findGamesEntities(int maxResults, int firstResult) {
        return findGamesEntities(false, maxResults, firstResult);
    }

    private List<Games> findGamesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Games.class));
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

    public Games findGames(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Games.class, id);
        } finally {
            em.close();
        }
    }

    public int getGamesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Games> rt = cq.from(Games.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
