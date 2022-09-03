package com.dao;

import com.entity.OrdersEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrdersDao extends GenericDao<OrdersEntity> {

    private final EntityManagerFactory entityManagerFactory;

    public OrdersDao(EntityManagerFactory entityManagerFactory) {
        super(OrdersEntity.class);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public EntityManager getEntityManager() {
        try {
            return this.entityManagerFactory.createEntityManager();
        }
        catch(Exception ex) {
            System.out.println("This Entity Manager cannot be created");
        }
        return null;
    }

    public List<OrdersEntity> find(String name) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrdersEntity> query = criteriaBuilder.createQuery(OrdersEntity.class);

        Root<OrdersEntity> c = query.from(OrdersEntity.class);
        ParameterExpression<String> paramName = criteriaBuilder.parameter(String.class);
        query.select(c).where(criteriaBuilder.equal(c.get("ordername"), paramName));
        TypedQuery<OrdersEntity> q = entityManager.createQuery(query);
        q.setParameter(paramName, name);

        return q.getResultList();
    }

    public List<OrdersEntity> findID() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrdersEntity> query = criteriaBuilder.createQuery(OrdersEntity.class);

        Root<OrdersEntity> from = query.from(OrdersEntity.class);
        query.orderBy(criteriaBuilder.desc(from.get("ordersId")));
        TypedQuery<OrdersEntity> q = entityManager.createQuery(query);
        return q.getResultList();
    }


}
