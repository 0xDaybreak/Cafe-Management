package com.dao;

import com.entity.CoffeeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CoffeeDao extends GenericDao<CoffeeEntity> {

    private final EntityManagerFactory entityManagerFactory;

    public CoffeeDao(EntityManagerFactory entityManagerFactory) {
        super(CoffeeEntity.class);
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
}
