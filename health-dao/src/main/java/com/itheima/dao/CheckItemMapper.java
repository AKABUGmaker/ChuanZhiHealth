package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {

    void add(CheckItem checkItem);

    List<CheckItem> findPage(@Param("queryString") String queryString);

    CheckItem findById(@Param("id") Integer id);

    void edit(CheckItem checkItem);

    void delete(@Param("id") Integer id);

    int findCountById(@Param("id") Integer id);

    List<CheckItem> findAll();
}
