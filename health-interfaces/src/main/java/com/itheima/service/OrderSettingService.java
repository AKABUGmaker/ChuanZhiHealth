package com.itheima.service;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void setNumberByDate(OrderSetting orderSetting);

    void setNumberByDateBatch(List<OrderSetting> orderSettings);

    List<Map> findByMouth(String mouth);
}
