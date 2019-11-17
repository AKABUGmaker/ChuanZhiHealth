package com.itheima.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.pojo.ReportDateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportDao reportDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Integer> getMemberReport(List<String> months) {

        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            Integer count = reportDao.getMemberReport(month);
            memberCount.add(count);
        }
        return memberCount;

    }

    @Override
    public List<Map> getSetmealReport() {

        return reportDao.getSetmealReport();
    }

    @Override
    public ReportDateVo getBusinessReportData() {
        DateTime now = DateTime.now();
        String format = "yyyy-MM-dd";
        String nowStr = now.toString(format);
        //本周第一天
        String beginOfWeek = DateUtil.beginOfWeek(now).toString(format);
//        本月第一天
        String beginOfMonth = DateUtil.beginOfMonth(now).toString(format);
        //本周最后一天
        String endOfWeek = DateUtil.endOfWeek(now).toString(format);
        //本月最后一天
        String endOfMonth = DateUtil.endOfMonth(now).toString(format);

        return ReportDateVo.builder()
                .reportDate(nowStr)
                .todayNewMember(memberDao.findMemberCountByDate(nowStr))
                .totalMember(memberDao.findMemberTotalCount())
                .thisWeekNewMember(memberDao.findMemberCountAfterDate(beginOfWeek))
                .thisMonthNewMember(memberDao.findMemberCountAfterDate(beginOfMonth))
                .todayOrderNumber(orderDao.findOrderCountByDate(nowStr))
                .todayVisitsNumber(orderDao.findVisitsCountByDate(nowStr))
                .thisWeekOrderNumber(orderDao.findOrderCountBetween(beginOfWeek, endOfWeek))
                .thisWeekVisitsNumber(orderDao.findVisitsOrderCountBetween(beginOfWeek, endOfWeek))
                .thisMonthOrderNumber(orderDao.findOrderCountBetween(beginOfMonth, endOfMonth))
                .thisMonthVisitsNumber(orderDao.findVisitsOrderCountBetween(beginOfMonth, endOfMonth))
                .hotSetmeal(orderDao.findHotSetmeal())
                .build();

    }
}
