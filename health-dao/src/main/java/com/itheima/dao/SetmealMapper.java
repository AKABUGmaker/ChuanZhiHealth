package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {

    void add(Setmeal setmeal);

    void setCheckGroupAndSetmealRelation(@Param("checkgroupId") Integer checkgroupId,@Param("id") Integer id);

    List<Setmeal> findPage(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(@Param("id") Integer id);

    List<CheckGroup> findCheckGroupBySetmealId(@Param("id") Integer id);

    List<CheckItem> findCheckItemByCheckGroupId(@Param("id") Integer id);

    List<CheckItem> findCheckItemsByCheckGroupIdBatch(List<Integer> checkGroupIds);
}
