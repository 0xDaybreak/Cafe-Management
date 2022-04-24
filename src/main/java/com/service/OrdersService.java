package com.service;

import com.dao.OrdersDao;
import com.entity.OrdersEntity;
import org.hibernate.criterion.Order;

import javax.persistence.Persistence;
import java.util.List;

public class OrdersService {

    private OrdersDao ordersDao;

    public OrdersService() {
        try {
            ordersDao = new OrdersDao(Persistence.createEntityManagerFactory("default"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addOrder(OrdersEntity newOrder) { ordersDao.create(newOrder); }

    public void updateOrder(OrdersEntity updatedOrder) {
        ordersDao.update(updatedOrder);
    }

    public List<OrdersEntity> getAllOrders() {
        return ordersDao.findAll();
    }

    public OrdersEntity findOrder(String userid) throws Exception {

        List<OrdersEntity> orders = ordersDao.find(userid);
        if(orders.size()==0) {
            throw new Exception("Order not found");
        }
        OrdersEntity o = orders.get(0);

        return o;
    }
    public List<OrdersEntity> findID() {return ordersDao.findID();}
}
