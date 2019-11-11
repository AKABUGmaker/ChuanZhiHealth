package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyUserService  implements UserDetailsService {
    //模拟数据库中的用户数据
    public  static Map<String, User> map = new HashMap<>();
    static {
        com.itheima.pojo.User user1 = new com.itheima.pojo.User();
        user1.setUsername("admin");
        user1.setPassword("$2a$10$OZq/FUL5ouu3.478pvPzh.AMrDoYqSevgoRfwjkErnTzXrLthu18a");

        com.itheima.pojo.User user2 = new com.itheima.pojo.User();
        user2.setUsername("xiaoming");
        user2.setPassword("$2a$10$OZq/FUL5ouu3.478pvPzh.AMrDoYqSevgoRfwjkErnTzXrLthu18a");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    /**
     *
     * @param username 就是用户在登录页面输入的
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = map.get(username);
        if(null == user){
            return null;
        }
        //查询用户名 密码告诉框架
        //查询用户的权限告诉框架

        List authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority("新增检查项"));
        authorities.add(new SimpleGrantedAuthority("查询检查项"));
        if("xiaoming".equals(username)){
            authorities.add(new SimpleGrantedAuthority("删除检查项"));
        }
        return new org.springframework.security.core.userdetails.User(   user.getUsername(),user.getPassword(),authorities);

    }

    public static void main(String[] args) {
//        String md5 = Md5.md5("12%bsahdkahksafsa345".getBytes());
//        System.out.println(md5 + "asdkjahkasnfkjfhowijehorw1232131231231_adsadsadad_3");

//        new BCryptPasswordEncoder();
//        String pwd = "1111";
//        long start = System.currentTimeMillis();
//        String encode = new BCryptPasswordEncoder().encode(pwd);
//        long end = System.currentTimeMillis();
//        System.out.println((end - start));
//
//
//        long startmd5 = System.currentTimeMillis();
//        String md5 = Md5.md5(pwd.getBytes());
//        long endmd5 = System.currentTimeMillis();
//        System.out.println((endmd5 - startmd5));

        String encode = new BCryptPasswordEncoder().encode("1234");
        System.out.println(encode);
        //$2a$10$Wsyv.NLuR9K0zNGEnW5VHumYYDOo42qMpG7EgnaSTa201/CQVcqsy
        //$2a$10$OZq/FUL5ouu3.478pvPzh.AMrDoYqSevgoRfwjkErnTzXrLthu18a

    }
}
