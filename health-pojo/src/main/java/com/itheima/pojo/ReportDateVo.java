package com.itheima.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReportDateVo implements Serializable {
    private String reportDate;//报表日期
    private Integer todayNewMember = 0;//今日新增会员数
    private Integer totalMember = 0;//总会员数
    private Integer thisWeekNewMember = 0;//本周新增会员数
    private Integer thisMonthNewMember = 0;//本月新增会员数

    private Integer todayOrderNumber = 0;//今日新增预约数
    private Integer todayVisitsNumber = 0;//今日到诊数
    private Integer thisWeekOrderNumber = 0;//本周预约数
    private Integer thisWeekVisitsNumber = 0;//本周到诊数
    private Integer thisMonthOrderNumber = 0;//本月预约数
    private Integer thisMonthVisitsNumber = 0;//本月到诊数
    private List<HotSetmealVo> hotSetmeal = new ArrayList<>();
    //    hotSetmeal :[
//    {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
//    {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
//                    ]
}
