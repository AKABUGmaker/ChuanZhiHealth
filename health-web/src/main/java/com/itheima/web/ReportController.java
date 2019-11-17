package com.itheima.web;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.HotSetmealVo;
import com.itheima.pojo.ReportDateVo;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    ReportService reportService;


    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //得到过去1年的月份
        DateTime dateTime = DateUtil.offsetMonth(new Date(), -12);
        List<String> months = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            DateTime dateTime1 = DateUtil.offsetMonth(dateTime, i);
            months.add(dateTime1.toString("yyyy-MM"));
        }

        List<Integer> memberCount = reportService.getMemberReport(months);

        Map map = new HashMap();

        map.put("months", months);
        map.put("memberCount", memberCount);
        return Result.success("", map);

    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map> maps = reportService.getSetmealReport();
        return Result.success("", maps);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        ReportDateVo reportDateVo = reportService.getBusinessReportData();
        return Result.success("", reportDateVo);
    }

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletResponse response, HttpServletRequest request) {

        try {

            ReportDateVo reportDataVo = reportService.getBusinessReportData();

            //C:\template
            String path = request.getSession().getServletContext().getRealPath("template");


//            1、使用poi提供excel（ xssf）加载报表模板
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(path + "\\report_template.xlsx");
//            2、获取sheet
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
//            3、获取row 获取cell 设置cell value
            sheetAt.getRow(2).getCell(5).setCellValue(reportDataVo.getReportDate());

            sheetAt.getRow(4).getCell(5).setCellValue(reportDataVo.getTodayNewMember());
            sheetAt.getRow(4).getCell(7).setCellValue(reportDataVo.getTotalMember());
            sheetAt.getRow(5).getCell(5).setCellValue(reportDataVo.getThisWeekNewMember());
            sheetAt.getRow(5).getCell(7).setCellValue(reportDataVo.getThisMonthNewMember());

            sheetAt.getRow(7).getCell(5).setCellValue(reportDataVo.getTodayOrderNumber());
            sheetAt.getRow(7).getCell(7).setCellValue(reportDataVo.getTodayVisitsNumber());
            sheetAt.getRow(8).getCell(5).setCellValue(reportDataVo.getThisWeekOrderNumber());
            sheetAt.getRow(8).getCell(7).setCellValue(reportDataVo.getThisWeekVisitsNumber());
            sheetAt.getRow(9).getCell(5).setCellValue(reportDataVo.getThisMonthOrderNumber());
            sheetAt.getRow(9).getCell(7).setCellValue(reportDataVo.getThisMonthVisitsNumber());

            int i = 12;
            for (HotSetmealVo hotSetmealVo : reportDataVo.getHotSetmeal()) {
                sheetAt.getRow(i).getCell(4).setCellValue(hotSetmealVo.getName());//套餐名称
                sheetAt.getRow(i).getCell(5).setCellValue(hotSetmealVo.getSetmealCount());//预约数量
                sheetAt.getRow(i).getCell(6).setCellValue(hotSetmealVo.getProportion().doubleValue());//占比
                i++;
            }


//            直接把excel写到浏览器

            //通响应头 （Response Header）告诉浏览器我现在写是一个文件，并且还是一个excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//
            //文件直接在浏览器上显示或者在访问时弹出文件下载对话框
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("运营报表", "UTF-8") + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            xssfWorkbook.write(out);

            out.flush();
            out.close();
            xssfWorkbook.close();
//            PrintWriter writer = response.getWriter();
//            writer.print("hello world");
//            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

