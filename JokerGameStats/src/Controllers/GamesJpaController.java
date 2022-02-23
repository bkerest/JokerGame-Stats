/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Database.Draws;
import Database.Games;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author vker
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
        if (games.getDrawsList() == null) {
            games.setDrawsList(new ArrayList<Draws>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Draws> attachedDrawsList = new ArrayList<Draws>();
            for (Draws drawsListDrawsToAttach : games.getDrawsList()) {
                drawsListDrawsToAttach = em.getReference(drawsListDrawsToAttach.getClass(), drawsListDrawsToAttach.getDrawid());
                attachedDrawsList.add(drawsListDrawsToAttach);
            }
            games.setDrawsList(attachedDrawsList);
            em.persist(games);
            for (Draws drawsListDraws : games.getDrawsList()) {
                Games oldGameidOfDrawsListDraws = drawsListDraws.getGameid();
                drawsListDraws.setGameid(games);
                drawsListDraws = em.merge(drawsListDraws);
                if (oldGameidOfDrawsListDraws != null) {
                    oldGameidOfDrawsListDraws.getDrawsList().remove(drawsListDraws);
                    oldGameidOfDrawsListDraws = em.merge(oldGameidOfDrawsListDraws);
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
            List<Draws> drawsListOld = persistentGames.getDrawsList();
            List<Draws> drawsListNew = games.getDrawsList();
            List<Draws> attachedDrawsListNew = new ArrayList<Draws>();
            for (Draws drawsListNewDrawsToAttach : drawsListNew) {
                drawsListNewDrawsToAttach = em.getReference(drawsListNewDrawsToAttach.getClass(), drawsListNewDrawsToAttach.getDrawid());
                attachedDrawsListNew.add(drawsListNewDrawsToAttach);
            }
            drawsListNew = attachedDrawsListNew;
            games.setDrawsList(drawsListNew);
            games = em.merge(games);
            for (Draws drawsListOldDraws : drawsListOld) {
                if (!drawsListNew.contains(drawsListOldDraws)) {
                    drawsListOldDraws.setGameid(null);
                    drawsListOldDraws = em.merge(drawsListOldDraws);
                }
            }
            for (Draws drawsListNewDraws : drawsListNew) {
                if (!drawsListOld.contains(drawsListNewDraws)) {
                    Games oldGameidOfDrawsListNewDraws = drawsListNewDraws.getGameid();
                    drawsListNewDraws.setGameid(games);
                    drawsListNewDraws = em.merge(drawsListNewDraws);
                    if (oldGameidOfDrawsListNewDraws != null && !oldGameidOfDrawsListNewDraws.equals(games)) {
                        oldGameidOfDrawsListNewDraws.getDrawsList().remove(drawsListNewDraws);
                        oldGameidOfDrawsListNewDraws = em.merge(oldGameidOfDrawsListNewDraws);
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
            List<Draws> drawsList = games.getDrawsList();
            for (Draws drawsListDraws : drawsList) {
                drawsListDraws.setGameid(null);
                drawsListDraws = em.merge(drawsListDraws);
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
