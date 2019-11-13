package com.itheima.security;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {


    @Reference
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //告诉框架当前登录用户的用户名 密码 权限
        User user = userService.findByUserName(username);

        if(null == user){
            return null;
        }
        //用户名
        String userUsername = user.getUsername();
        //密码
        String userPassword = user.getPassword();
        //权限
        Set<Role> userRoles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRoles)){
            for (Role userRole : userRoles) {
                Set<Permission> permissions = userRole.getPermissions();
                if (CollectionUtil.isNotEmpty(permissions)){
                    for (Permission permission : permissions) {
                        authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }


       return new org.springframework.security.core.userdetails.User(userUsername,userPassword,authorities);



    }
}
