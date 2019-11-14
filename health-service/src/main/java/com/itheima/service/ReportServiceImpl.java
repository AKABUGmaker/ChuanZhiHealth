package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportDao reportDao;
    @Override
    public List<Integer> getMemberReport(List<String> months) {

        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            Integer count = reportDao.getMemberReport(month);
            memberCount.add(count);
        }
        return memberCount;

    }
}
