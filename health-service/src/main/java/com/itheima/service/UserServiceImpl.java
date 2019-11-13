package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionMapper;
import com.itheima.dao.RoleMapper;
import com.itheima.dao.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public User findByUserName(String username) {
        //根据用户名查询用户基本信息
        User user = userMapper.selectByUserName(username);
        if(null != user){
            //根据userid查询用户下面所有的角色
            Set<Role> roles = roleMapper.selectByUserId(user.getId());
            if(CollectionUtil.isNotEmpty(roles)){
                //循环用户所有角色查询角色对应所有的权限
                for (Role role : roles) {
                    Set<Permission> permissions = permissionMapper.selectByRoleId(role.getId());
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
