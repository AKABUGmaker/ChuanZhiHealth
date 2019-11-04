package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    //使用dubbo的时候抛异常需要在接口定义好 throws CheckItemException
    void delete(Integer id) throws CheckItemException;

    List<CheckItem> findAll();
}
