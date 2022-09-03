package com.dao;

import com.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao extends GenericDao<UserEntity>{

    private final EntityManagerFactory entityManagerFactory;

    public UserDao(EntityManagerFactory entityManagerFactory) {
        super(UserEntity.class);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public EntityManager getEntityManager() {
        try {
                return entityManagerFactory.createEntityManager();
        }
        catch (Exception e) {
            System.out.println("The entity manager cannot be created");
        }
        return null;
    }

    //for login
    public List<UserEntity> find(String name) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);

        Root<UserEntity> c = query.from(UserEntity.class);
        ParameterExpression<String> paramName = criteriaBuilder.parameter(String.class);
        query.select(c).where(criteriaBuilder.equal(c.get("username"), paramName));
        TypedQuery<UserEntity> q = entityManager.createQuery(query);
        q.setParameter(paramName, name);

        return q.getResultList();
    }
}
