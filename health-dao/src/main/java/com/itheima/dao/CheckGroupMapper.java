package com.itheima.dao;

import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {

    /**
     * map & pojo 不需要加@Param
     * 多参数建议加@Param ,不加就需要按照param1 param2 ...paramN
     * List & Array 可以不加@Param  如果不加@Param取值需要写list&array
     * 如果有多个List参数那么取值  param1 param2 ...paramN
     */

    void add(CheckGroup checkGroup);

    void setCheckItemAndCheckGroupRelation(@Param("checkitemId") Integer checkitemId, @Param("groupId") Integer groupId);

    List<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroup(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);

    void setCheckItemAndCheckGroupRelation2(List<Map> params);

    List<CheckGroup> findAll();
}
