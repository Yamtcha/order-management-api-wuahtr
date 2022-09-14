package com.order.management.api.yamkelavenfolo.addAndRemoveItemTests;

import com.order.management.api.yamkelavenfolo.DAO.Ibatis.IbitisOrderRepository;
import com.order.management.api.yamkelavenfolo.DAO.Orders;
import com.order.management.api.yamkelavenfolo.OrderManagementApplication;
import com.order.management.api.yamkelavenfolo.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = {OrderService.class, OrderManagementApplication.class})
@RunWith(SpringRunner.class)
public class AddItemTests {

    @MockBean
    IbitisOrderRepository ordersDAO;

    @Autowired
    OrderService orderService;

    private List<Orders> ordersList = new ArrayList<>();

    @Before
    public void init() {
        Orders orderA = new Orders("262516", "545631534154165", "microwave", 2, new BigDecimal("10000.85"), true);
        Orders orderB = new Orders("156168", "545631533154165", "xbox", 1, new BigDecimal("120000.85"), false);
        Orders orderC = new Orders("16185", "545631539154165", "play station", 1, new BigDecimal("504006.85"), true);
        Orders orderD = new Orders("195161", "645631539154165", "laptop", 1, new BigDecimal("504006.85"), true);
        Orders orderE = new Orders("699849", "745631539154165", "stove", 1, new BigDecimal("504006.85"), true);

        ordersList.add(orderA);
        ordersList.add(orderB);
        ordersList.add(orderC);
        ordersList.add(orderD);
        ordersList.add(orderE);
    }

    @Test
    public void itemOrderNotFoundTest() {
        Mockito.when(ordersDAO.selectOrders()).thenReturn(ordersList);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.increaseItemQuantity("1234567845","play station");
        });

        assertEquals(exception.getMessage(),"Order=" + "1234567845" + " doesn't exist or has been deleted.");
    }

    @Test
    public void itemNotFoundTest() {
        Mockito.when(ordersDAO.selectOrders()).thenReturn(ordersList);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.increaseItemQuantity("545631539154165","laptop");
        });

        assertEquals(exception.getMessage(),"item=" + "laptop" + " is not part of the order");
    }
}
