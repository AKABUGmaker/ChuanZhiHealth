package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.utils.JedisUtil;
import com.itheima.utils.SmsUtil;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        String codeString = String.valueOf(code);

        SmsUtil.sendSmsCode(telephone,codeString);

        //把验证码存入redis(为了提交时与用户输入的仅从比对)
        jedisUtil.setex("001"+telephone,60*500,codeString);


        return Result.success("");
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){

        Integer code = ValidateCodeUtils.generateValidateCode(4);
        String codeString = String.valueOf(code);

        SmsUtil.sendSmsCode(telephone,codeString);

        //把验证码存入redis(为了提交时与用户输入的仅从比对)
        jedisUtil.setex("002"+telephone,60*500,codeString);
        return Result.success("");
    }
}
