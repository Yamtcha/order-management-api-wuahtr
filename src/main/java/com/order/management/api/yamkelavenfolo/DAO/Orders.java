package com.order.management.api.yamkelavenfolo.DAO;

import java.math.BigDecimal;

public class Orders {
    private String dispatcherCode;

    private String orderNumber;

    private String nameOfItem;

    private int quantity;

    private BigDecimal price;

    private boolean isActive;

    public Orders(String dispatcherCode, String orderNumber, String nameOfItem, int quantity, BigDecimal price, boolean isActive) {
        this.dispatcherCode = dispatcherCode;
        this.orderNumber = orderNumber;
        this.nameOfItem = nameOfItem;
        this.quantity = quantity;
        this.price = price;
        this.isActive = isActive;
    }

    public Orders() {

    }

    public String getDispatcherCode() {
        return dispatcherCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
