package com.itheima.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {

    Integer getMemberReport(String month);

    List<Map> getSetmealReport();

}
