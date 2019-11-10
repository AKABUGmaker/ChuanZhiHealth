package com.itheima.controller;


import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class MobileSetmealController {


    @Reference
    SetmealService setmealService;

    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){

        List<Setmeal> setmeals = setmealService.getSetmeal();

        return Result.success("",setmeals);
    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id){
        Setmeal setmeal = setmealService.findDetailById(id);

        return Result.success("",setmeal);


    }

    @RequestMapping("/findById")
    public Result findById(Integer id){

        Setmeal setmeal = setmealService.findByIdBatch58(id);

        return Result.success("",setmeal);
    }

    @RequestMapping("/getToken")
    public Result getToken(){
        String token = UUID.randomUUID().toString();
        jedisUtil.setex(token,60*30,token);
        return Result.success("",token);
    }




}
