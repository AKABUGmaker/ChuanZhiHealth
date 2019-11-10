package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    void add(Setmeal setmeal);

    PageResult findpage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    Setmeal findByIdBatch(Integer id);

    Setmeal findByIdBatch58(Integer id);

    Setmeal findDetailById(Integer id);
}
