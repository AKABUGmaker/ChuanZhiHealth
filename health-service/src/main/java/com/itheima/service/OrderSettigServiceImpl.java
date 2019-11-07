package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettigServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Override
    public void setNumberByDate(OrderSetting orderSetting) {

        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询数据库中是否已经有数据
        Integer count = orderSettingMapper.findCountByDate(orderDate);
        //有数据就更新
        if (null != count && count>0){
        orderSettingMapper.edit(orderSetting);
        }else {
        //没有数据就插入
        orderSettingMapper.add(orderSetting);
        }
    }

    @Override
    public void setNumberByDateBatch(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            setNumberByDate(orderSetting);
        }

        //使用多线程导入,把orderSetting拆分成小的集合,创建多个线程分摊人数
        //TODO
    }

    @Override
    public List<Map> findByMouth(String mouth) {

        List<Map> result = new ArrayList<>();

        String start = mouth + "-01";
        String end = mouth + "-31";
        List<OrderSetting> orderSettings = orderSettingMapper.findByMouth(start,end);

        if(CollectionUtil.isNotEmpty(orderSettings)){
            for (OrderSetting orderSetting : orderSettings) {

                Date orderDate = orderSetting.getOrderDate();
                Map map = new HashMap<>();
                map.put("date",orderDate.getDate());
                map.put("mouth",orderDate.getMonth());
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                result.add(map);
            }
        }
        return result;

    }

}
