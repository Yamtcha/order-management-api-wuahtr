package com.order.management.api.yamkelavenfolo.controllers;

import com.order.management.api.yamkelavenfolo.DAO.Orders;
import com.order.management.api.yamkelavenfolo.constants.OrderConstants;
import com.order.management.api.yamkelavenfolo.exceptions.InvalidException;
import com.order.management.api.yamkelavenfolo.exceptions.NotFoundException;
import com.order.management.api.yamkelavenfolo.model.errorHandling.ErrorDetail;
import com.order.management.api.yamkelavenfolo.model.response.ResponseEntity;
import com.order.management.api.yamkelavenfolo.model.success.Success;
import com.order.management.api.yamkelavenfolo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Orders")
public class OrderManagementController {
    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    private ResponseEntity getOrders() {
        try {
            List<Orders> allOrders = orderService.getOrders();
            return new ResponseEntity(HttpStatus.OK.toString(), allOrders);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail("", HttpStatus.BAD_REQUEST.toString(), exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }

    @GetMapping("/{orderNumber}")
    private ResponseEntity getOrdersByOrderNumber(@PathVariable String orderNumber) {
        try {
            List<Orders> allOrders = orderService.getOrdersByOrderNumber(orderNumber);
            return new ResponseEntity(HttpStatus.OK.toString(), allOrders);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail(orderNumber, OrderConstants.ORDER_NOT_FOUND, exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }

    @DeleteMapping("/delete/{orderNumber}")
    private ResponseEntity deleteOrder(@PathVariable String orderNumber) throws NotFoundException, InvalidException {
        try {
            orderService.deleteOrder(orderNumber);
            Success success = new Success("Order deleted successfully");
            return new ResponseEntity(HttpStatus.OK.toString(), success);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail(orderNumber, OrderConstants.ORDER_NOT_FOUND, exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }

    @PostMapping("/update/quantity/add")
    private ResponseEntity increaseItemQuantity(@RequestBody Orders order) {
        try {
            orderService.increaseItemQuantity(order.getOrderNumber(), order.getNameOfItem());
            Success success = new Success("Order item successfully added");
            return new ResponseEntity(HttpStatus.OK.toString(), success);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail(order.getOrderNumber(), OrderConstants.ORDER_NOT_FOUND, exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }

    @PostMapping("/update/quantity/remove")
    private ResponseEntity decreaseItemQuantity(@RequestBody Orders order) {
        try {
            orderService.decreaseItemQuantity(order.getOrderNumber(), order.getNameOfItem());
            Success success = new Success("Order item successfully removed");
            return new ResponseEntity(HttpStatus.OK.toString(), success);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail(order.getOrderNumber(), OrderConstants.ORDER_NOT_FOUND, exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }

    @PostMapping("/create")
    private ResponseEntity createOrder(@RequestBody Orders createOrder) {
        try {
            if (createOrder.getOrderNumber().isEmpty() || createOrder.getDispatcherCode().isEmpty()) {
                throw new RuntimeException("order number or dispatcher code can not be null");
            }
            orderService.createOrder(createOrder);
            Success success = new Success("Order successfully created");
            return new ResponseEntity(HttpStatus.OK.toString(), success);
        } catch (RuntimeException exception) {
            ErrorDetail errorDetail = new ErrorDetail(createOrder.getOrderNumber(), HttpStatus.BAD_REQUEST.toString(), exception.getMessage(), "400");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }
}
