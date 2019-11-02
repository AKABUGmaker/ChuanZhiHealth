package com.itheima.dao;

import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {

    void add(CheckItem checkItem);

    List<CheckItem> findPage(String queryString);
}
