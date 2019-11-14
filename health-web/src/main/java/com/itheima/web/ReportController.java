package com.itheima.web;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    ReportService reportService;


    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        //得到过去1年的月份
        DateTime dateTime = DateUtil.offsetMonth(new Date(), -12);
        List<String> months = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            DateTime dateTime1 = DateUtil.offsetMonth(dateTime, i);
            months.add(dateTime1.toString("yyyy-MM"));
        }

        List<Integer> memberCount = reportService.getMemberReport(months);

        Map map = new HashMap();

        map.put("months",months);
        map.put("memberCount",memberCount);
        return Result.success("",map);

    }

}
