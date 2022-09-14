package com.order.management.api.yamkelavenfolo.service;

import com.order.management.api.yamkelavenfolo.DAO.Ibatis.IbitisOrderRepository;
import com.order.management.api.yamkelavenfolo.DAO.Orders;
import com.order.management.api.yamkelavenfolo.constants.OrderConstants;
import com.order.management.api.yamkelavenfolo.exceptions.InvalidException;
import com.order.management.api.yamkelavenfolo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    IbitisOrderRepository ordersDAO;
    /**
     * Get all the orders.
     *
     * @return {List of Orders}
     */

    public List<Orders> getOrders() {
        List<Orders> orders = ordersDAO.selectOrders();
        return orders.stream().filter(Orders::getIsActive).toList();
    }

    /**
     * One customer can make one order with multiple items.
     * Filter by Order Number to get all the items with the same order number.
     * /{orderNumber}
     *
     * @return {List of Orders}
     */

    public List<Orders> getOrdersByOrderNumber(String orderNumber) throws NotFoundException, InvalidException {
        try {
            if (orderNumber.length() >= OrderConstants.ORDER_MIN_LENGTH) {
                List<Orders> orders = ordersDAO.selectByOrderNumber(orderNumber);
                if (orders.isEmpty()) {
                    throw new NotFoundException("Order number=" + orderNumber + " was not found");
                } else {
                    return orders;
                }
            }
            throw new InvalidException("Order number=" + orderNumber + " is invalid, should be more than " + OrderConstants.ORDER_MIN_LENGTH);
        } catch (NotFoundException | InvalidException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Delete order with {orderNumber}
     * /delete/{orderNumber}
     */

    public void deleteOrder(String orderNumber) throws NotFoundException {
        List<Orders> ordersList = getOrdersByOrderNumber(orderNumber);
        ordersDAO.deleteByOrderNumber(orderNumber);
    }

    /**
     * Increase item quantity
     * /update/quantity/add/{orderNumber}/{itemName}
     * {Price} will be a constant value 50000 for the purpose of this API
     */

    public void increaseItemQuantity(String orderNumber, String itemName) throws NotFoundException {
        List<Orders> ordersList = getOrdersByOrderNumber(orderNumber);

        //Order List is empty and the item has not been added into the order yet.
        if (ordersList.isEmpty()) {
            throw new NotFoundException("Order=" + orderNumber + " doesn't exist or has been deleted.");
        } else {
            Optional<Orders> orders = ordersList.stream().filter(i -> i.getNameOfItem().equals(itemName) && i.getIsActive()).findFirst();

            if (orders.isEmpty()) {
                throw new NotFoundException("item=" + itemName + " is not part of the order");
            }

            Orders updatedOrders = new Orders(orders.get().getDispatcherCode(), orders.get().getOrderNumber()
                    , orders.get().getNameOfItem(), orders.get().getQuantity() + 1, orders.get().getPrice().add(OrderConstants.price), true);
            ordersDAO.update(updatedOrders);
        }
    }

    /**
     * Decrease item quantity
     * /update/quantity/remove/{orderNumber}/{itemName}
     * {Price} will be a constant value 50000 for the purpose of this API
     */

    public void decreaseItemQuantity(String orderNumber, String itemName) throws NotFoundException {
        List<Orders> ordersList = getOrdersByOrderNumber(orderNumber);
        //Order is empty and the item has not been added into the order yet.
        if (ordersList.isEmpty()) {
            throw new NotFoundException("Order=" + orderNumber + " doesn't exist");
        } else {

            Optional<Orders> orders = ordersList.stream().filter(i -> i.getNameOfItem().equals(itemName) && i.getIsActive()).findFirst();

            if (orders.isEmpty()) {
                throw new NotFoundException("item=" + itemName + " is not part of the order");
            }

            int updateQuantity = orders.get().getQuantity() - 1;
            //if updateQuantity is 0 then the item has been removed.
            Orders updatedOrders;
            if (updateQuantity != 0) {
                updatedOrders = new Orders(orders.get().getDispatcherCode(), orders.get().getOrderNumber()
                        , orders.get().getNameOfItem(), orders.get().getQuantity() - 1, orders.get().getPrice().subtract(OrderConstants.price), true);
            } else {
                updatedOrders = new Orders(orders.get().getDispatcherCode(), orders.get().getOrderNumber()
                        , orders.get().getNameOfItem(), orders.get().getQuantity() - 1, orders.get().getPrice().subtract(OrderConstants.price), false);
            }
            ordersDAO.update(updatedOrders);
        }
    }

    /**
     * Add order {Order}
     * /create
     */

    public void createOrder(Orders insertOrder) {
        List<Orders> orders = ordersDAO.selectByOrderNumber(insertOrder.getOrderNumber());;

        if (orders.isEmpty()) {
            Orders order = new Orders(insertOrder.getDispatcherCode(), insertOrder.getOrderNumber(), insertOrder.getNameOfItem(),
                    insertOrder.getQuantity(), insertOrder.getPrice(), true);
            ordersDAO.insert(order);
        } else {
            Optional<Orders> ordersInTheDatabase = orders.stream().filter(i -> i.getNameOfItem().equals(insertOrder.getNameOfItem())).findFirst();
            if (ordersInTheDatabase.isEmpty()) {
                Orders order = new Orders(insertOrder.getDispatcherCode(), insertOrder.getOrderNumber(), insertOrder.getNameOfItem(),
                        insertOrder.getQuantity(), insertOrder.getPrice(), true);
                ordersDAO.insert(order);
            } else {
                int updateQuantity = ordersInTheDatabase.get().getQuantity() + insertOrder.getQuantity();
                Orders updatedOrders = new Orders(ordersInTheDatabase.get().getDispatcherCode(), ordersInTheDatabase.get().getOrderNumber()
                        , ordersInTheDatabase.get().getNameOfItem(), updateQuantity, ordersInTheDatabase.get().getPrice().add(insertOrder.getPrice()), true);
                ordersDAO.update(updatedOrders);
            }
        }
    }
}