package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderInfoVo;

import java.util.Map;

public interface OrderService {
    Result submit(OrderInfoVo orderInfoVo);

    Map findById(Integer id);
}
