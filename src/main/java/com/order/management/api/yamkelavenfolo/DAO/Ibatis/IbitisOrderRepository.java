package com.order.management.api.yamkelavenfolo.DAO.Ibatis;

import com.order.management.api.yamkelavenfolo.DAO.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IbitisOrderRepository {

    @Select("SELECT * FROM orders WHERE orderNumber = #{orderNumber}")
    List<Orders> selectByOrderNumber(String orderNumber);

    @Select("SELECT * FROM orders LIMIT 10")
    List<Orders> selectOrders();

    @Delete("DELETE FROM orders WHERE orderNumber = #{orderNumber}")
    int deleteByOrderNumber(String orderNumber);

    @Insert("INSERT INTO orders(dispatcherCode, orderNumber, nameOfItem, quantity, price, isActive) " +
            "VALUES (#{dispatcherCode}, #{orderNumber}, #{nameOfItem}, #{quantity},#{price},#{isActive})")
    int insert(Orders orders);

    @Update("Update orders set price=#{price}, quantity=#{quantity} where dispatcherCode=#{dispatcherCode}")
    int update(Orders orders);
}
