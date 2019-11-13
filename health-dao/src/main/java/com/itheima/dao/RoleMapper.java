package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Set<Role> selectByUserId(Integer id);
}