/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Database.Draws;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Database.Games;
import Database.Prizecategories;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author vker
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
        if (draws.getPrizecategoriesList() == null) {
            draws.setPrizecategoriesList(new ArrayList<Prizecategories>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Games gameid = draws.getGameid();
            if (gameid != null) {
                gameid = em.getReference(gameid.getClass(), gameid.getGameid());
                draws.setGameid(gameid);
            }
            List<Prizecategories> attachedPrizecategoriesList = new ArrayList<Prizecategories>();
            for (Prizecategories prizecategoriesListPrizecategoriesToAttach : draws.getPrizecategoriesList()) {
                prizecategoriesListPrizecategoriesToAttach = em.getReference(prizecategoriesListPrizecategoriesToAttach.getClass(), prizecategoriesListPrizecategoriesToAttach.getPid());
                attachedPrizecategoriesList.add(prizecategoriesListPrizecategoriesToAttach);
            }
            draws.setPrizecategoriesList(attachedPrizecategoriesList);
            em.persist(draws);
            if (gameid != null) {
                gameid.getDrawsList().add(draws);
                gameid = em.merge(gameid);
            }
            for (Prizecategories prizecategoriesListPrizecategories : draws.getPrizecategoriesList()) {
                Draws oldDrawidOfPrizecategoriesListPrizecategories = prizecategoriesListPrizecategories.getDrawid();
                prizecategoriesListPrizecategories.setDrawid(draws);
                prizecategoriesListPrizecategories = em.merge(prizecategoriesListPrizecategories);
                if (oldDrawidOfPrizecategoriesListPrizecategories != null) {
                    oldDrawidOfPrizecategoriesListPrizecategories.getPrizecategoriesList().remove(prizecategoriesListPrizecategories);
                    oldDrawidOfPrizecategoriesListPrizecategories = em.merge(oldDrawidOfPrizecategoriesListPrizecategories);
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

    public void edit(Draws draws) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Draws persistentDraws = em.find(Draws.class, draws.getDrawid());
            Games gameidOld = persistentDraws.getGameid();
            Games gameidNew = draws.getGameid();
            List<Prizecategories> prizecategoriesListOld = persistentDraws.getPrizecategoriesList();
            List<Prizecategories> prizecategoriesListNew = draws.getPrizecategoriesList();
            if (gameidNew != null) {
                gameidNew = em.getReference(gameidNew.getClass(), gameidNew.getGameid());
                draws.setGameid(gameidNew);
            }
            List<Prizecategories> attachedPrizecategoriesListNew = new ArrayList<Prizecategories>();
            for (Prizecategories prizecategoriesListNewPrizecategoriesToAttach : prizecategoriesListNew) {
                prizecategoriesListNewPrizecategoriesToAttach = em.getReference(prizecategoriesListNewPrizecategoriesToAttach.getClass(), prizecategoriesListNewPrizecategoriesToAttach.getPid());
                attachedPrizecategoriesListNew.add(prizecategoriesListNewPrizecategoriesToAttach);
            }
            prizecategoriesListNew = attachedPrizecategoriesListNew;
            draws.setPrizecategoriesList(prizecategoriesListNew);
            draws = em.merge(draws);
            if (gameidOld != null && !gameidOld.equals(gameidNew)) {
                gameidOld.getDrawsList().remove(draws);
                gameidOld = em.merge(gameidOld);
            }
            if (gameidNew != null && !gameidNew.equals(gameidOld)) {
                gameidNew.getDrawsList().add(draws);
                gameidNew = em.merge(gameidNew);
            }
            for (Prizecategories prizecategoriesListOldPrizecategories : prizecategoriesListOld) {
                if (!prizecategoriesListNew.contains(prizecategoriesListOldPrizecategories)) {
                    prizecategoriesListOldPrizecategories.setDrawid(null);
                    prizecategoriesListOldPrizecategories = em.merge(prizecategoriesListOldPrizecategories);
                }
            }
            for (Prizecategories prizecategoriesListNewPrizecategories : prizecategoriesListNew) {
                if (!prizecategoriesListOld.contains(prizecategoriesListNewPrizecategories)) {
                    Draws oldDrawidOfPrizecategoriesListNewPrizecategories = prizecategoriesListNewPrizecategories.getDrawid();
                    prizecategoriesListNewPrizecategories.setDrawid(draws);
                    prizecategoriesListNewPrizecategories = em.merge(prizecategoriesListNewPrizecategories);
                    if (oldDrawidOfPrizecategoriesListNewPrizecategories != null && !oldDrawidOfPrizecategoriesListNewPrizecategories.equals(draws)) {
                        oldDrawidOfPrizecategoriesListNewPrizecategories.getPrizecategoriesList().remove(prizecategoriesListNewPrizecategories);
                        oldDrawidOfPrizecategoriesListNewPrizecategories = em.merge(oldDrawidOfPrizecategoriesListNewPrizecategories);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Games gameid = draws.getGameid();
            if (gameid != null) {
                gameid.getDrawsList().remove(draws);
                gameid = em.merge(gameid);
            }
            List<Prizecategories> prizecategoriesList = draws.getPrizecategoriesList();
            for (Prizecategories prizecategoriesListPrizecategories : prizecategoriesList) {
                prizecategoriesListPrizecategories.setDrawid(null);
                prizecategoriesListPrizecategories = em.merge(prizecategoriesListPrizecategories);
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
