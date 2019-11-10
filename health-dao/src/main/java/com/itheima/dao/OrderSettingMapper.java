package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {
    Integer findCountByDate(Date orderDate);

    void edit(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMouth(@Param("start") String start,@Param("end") String end);

    OrderSetting findByDate(@Param("orderDate") Date orderDate);

    void updateReservation(@Param("id") Integer id);

    int updateReservations(@Param("id") Integer id, @Param("version") int version);

}
