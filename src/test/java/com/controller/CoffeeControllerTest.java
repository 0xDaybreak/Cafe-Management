package com.controller;

import com.service.OrdersService;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeControllerTest {

    @Test
    void checkIfDateIsTruncatedCorrectly() {
        var coffeeController = new CoffeeController();
        var date = new Date(1997, Calendar.MARCH, 13);
        assertEquals(date,coffeeController.truncToSec(date));
    }

    @Test
    void checkOrdersIdIsSetCorrectly() {
        var ordersService = new OrdersService();
        assertEquals(44,ordersService.findID().get(0).getOrdersId()); // mocking
    }

}