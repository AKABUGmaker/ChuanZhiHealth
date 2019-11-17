package com.itheima.service;

import com.itheima.pojo.ReportDateVo;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Integer> getMemberReport(List<String> months);

    List<Map> getSetmealReport();

    ReportDateVo getBusinessReportData();

}
