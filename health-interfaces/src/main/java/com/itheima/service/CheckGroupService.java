package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupService {

    void add(CheckGroup checkGroup);


    PageResult findPage(QueryPageBean queryPageBean);

    Map findById4Update(Integer id);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();

}
