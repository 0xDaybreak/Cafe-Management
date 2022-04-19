package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class GenericDao<T> {

    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    public void create(T entity) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch(RuntimeException e) {

            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        }
    public void update(T entity) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge((entity));
            entityManager.getTransaction().commit();
        }
        catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }
    }
    public void remove(T entity, int entityId) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove((T) entityManager.find(this.entityClass, entityId));
            entityManager.getTransaction().commit();
        }
        catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

    }

    public T find(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            T ret = entityManager.find(this.entityClass, id);
            return ret;
        }
        catch (RuntimeException e) {
          entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }
        return null;
    }
    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(entityClass));
            List<T> returnValues = (List<T>)  entityManager.createQuery(criteriaQuery).getResultList();
            return returnValues;
        } catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }
        return null;
    }
}
