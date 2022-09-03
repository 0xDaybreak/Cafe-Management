package com.service;

import com.dao.CoffeeDao;
import com.entity.CoffeeEntity;

import javax.persistence.Persistence;

public class CoffeeService {

    private CoffeeDao coffeeDao;

    public CoffeeService() {
        try {
            coffeeDao = new CoffeeDao(Persistence.createEntityManagerFactory("default"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addCoffee(CoffeeEntity coffeeEntity) { coffeeDao.create(coffeeEntity); }
}
