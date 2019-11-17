package com.itheima.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HotSetmealVo  implements Serializable {
    private String name;
    private Integer setmealCount;
    private BigDecimal proportion; //比double精度更高
}
