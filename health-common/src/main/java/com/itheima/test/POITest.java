package com.itheima.test;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {

    @Test
    public void method() throws IOException {
        //创建Excel

        //1,创建Excel对象(工作薄(多表))
        XSSFWorkbook workbook = new XSSFWorkbook();

        //2,创建sheet(工作表)
        XSSFSheet sheet = workbook.createSheet();

        //3,创建Row
        XSSFRow row0 = sheet.createRow(0);
        XSSFRow row1 = sheet.createRow(1);
        XSSFRow row2 = sheet.createRow(2);

        //4,创建Cell
        //5,给单元格设置数据
        row0.createCell(0).setCellValue("姓名");
        row0.createCell(1).setCellValue("地址");
        row0.createCell(2).setCellValue("爱好");

        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("航头");
        row1.createCell(2).setCellValue("吸烟");

        row2.createCell(0).setCellValue("李四");
        row2.createCell(1).setCellValue("航头");
        row2.createCell(2).setCellValue("喝酒");


        //6,给内存里面Excel对象写入文件
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Test.xlsx");

        workbook.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Test
    public void method2() throws IOException {

        //读取Excel

        //1.加载Excel
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\Test.xlsx");

        //2.获取Sheet
        XSSFSheet sheet = workbook.getSheetAt(0);//获取表1(根据下标)

        //3.获取Row
        //4.获取Cell
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum() ; j++) {
                XSSFCell cell = row.getCell(j);
        //5.获取单元格数据
                System.out.print(cell.getStringCellValue()+",");
            }
            System.out.println();
        }

        workbook.close();

    }
}
