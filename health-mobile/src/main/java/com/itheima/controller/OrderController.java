package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfoVo;
import com.itheima.service.OrderService;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    JedisUtil jedisUtil;

    @Reference
    OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderInfoVo orderInfoVo) {

        //获取redis中的验证码
        String redisTelephone = jedisUtil.get("001" + orderInfoVo.getTelephone());

        //获取用户输入的验证码并比较
        String orderInfoValidateCode = orderInfoVo.getValidateCode();

        String token = orderInfoVo.getToken();

        if(null == token){
            return Result.error("非法请求");
        }

        Long del = jedisUtil.del(token);

        if (del == 0){
            return Result.error("非法请求");
        }

        if (null == redisTelephone || !redisTelephone.equals(orderInfoValidateCode)) {
            return Result.error("验证码错误");
        }

        //设置预约类型为微信预约
        orderInfoVo.setOrderType(Order.ORDERTYPE_WEIXIN);
        //调用预约接口

        return orderService.submit(orderInfoVo);


    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map = orderService.findById(id);

        return Result.success("",map);
    }
}
