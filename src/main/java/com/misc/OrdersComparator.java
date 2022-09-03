package com.misc;

import com.entity.OrdersEntity;

import java.util.Comparator;

public class OrdersComparator implements Comparator<OrdersEntity> {
    @Override
    public int compare(OrdersEntity o1, OrdersEntity o2) {
        return Double.compare(o1.getPrice(),o2.getPrice());
    }
}
