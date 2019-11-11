package com.itheima.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.entity.Result;
import com.itheima.pojo.LoginInfoVo;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JedisUtil jedisUtil;

    @Reference
    MemberService memberService;

    @RequestMapping("/login")
    public Result login(@RequestBody LoginInfoVo loginInfoVo, HttpServletRequest request){

        String telephone = loginInfoVo.getTelephone();
        String validateCode = loginInfoVo.getValidateCode();

        String redisCode = jedisUtil.get("002" + telephone);

        if(null == redisCode || !redisCode.equals(validateCode)){
            return Result.error("验证码错误");
        }

        Member member = memberService.findByTelephone(telephone);


        if(null == member){
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }

       // request.getSession().setAttribute("member",member);

        String token = UUID.randomUUID().toString();
        jedisUtil.setex(token,60*120, JSON.toJSONString(member));


        return Result.success("",token);



    }
}
