package com.itheima.dao;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {

    void add(Setmeal setmeal);

    void setCheckGroupAndSetmealRelation(@Param("checkgroupId") Integer checkgroupId,@Param("id") Integer id);

    List<Setmeal> findPage(String queryString);
}
