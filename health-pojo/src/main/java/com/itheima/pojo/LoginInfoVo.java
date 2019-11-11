package com.itheima.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfoVo implements Serializable {
    private String telephone;
    private String validateCode;
}
