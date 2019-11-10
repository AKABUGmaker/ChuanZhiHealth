package com.itheima.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInfoVo implements Serializable {
//
//    idCard: "422801199207211413"
//    name: "1"
//    orderDate: "2019-11-11"
//    setmealId: "12"
//    sex: "1"
//    telephone: "15601795090"
//    validateCode: "2"

    private String idCard;//身份证
    private String name;//名称
    private Date orderDate;//预约日期
    private Integer setmealId;//套餐id
    private Integer sex;//性别
    private String telephone;//手机号码
    private String validateCode;//验证码

    private String token;

    private String orderType; //预约类型





}
