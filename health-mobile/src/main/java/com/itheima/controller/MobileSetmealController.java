package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class MobileSetmealController {


    @Reference
    SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){

        List<Setmeal> setmeals = setmealService.getSetmeal();

        return Result.success("",setmeals);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){

        Setmeal setmeal = setmealService.findByIdBatch58(id);

        return Result.success("",setmeal);
    }


}
