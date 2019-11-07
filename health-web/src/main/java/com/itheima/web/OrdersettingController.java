package com.itheima.web;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/setNumberByDate")
    public Result setNumberByDate(@RequestBody OrderSetting orderSetting) {

        orderSettingService.setNumberByDate(orderSetting);
        return Result.success("");

    }


    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {

        try {
            //使用excel工具读取文件
            //返回读取到的excel中的数据
            List<String[]> datas = POIUtils.readExcel(excelFile);

            //把datas转成List<OrderSetting>
            List<OrderSetting> orderSettings = new ArrayList<>();

            if (CollectionUtil.isNotEmpty(datas)) {
                for (String[] data : datas) {
                    //datas的第0行为日期
                    if (null != data && data.length == 2) {
                        String dateStr = data[0];
                        //datas的第1行为预约数
                        String numberStr = data[1];

                        DateTime orderDate = null;
                        Integer number = null;

                        try {
                            //防止用户乱写日期
                            orderDate = DateUtil.parse(dateStr, "yyyy/MM/dd");
                            //防止用户乱写数字
                            number = Integer.valueOf(numberStr);
                        } catch (Exception e) {
                            continue;
                        }


                        OrderSetting orderSetting = new OrderSetting(orderDate, number);
                        orderSettings.add(orderSetting);
                    }

                }
            }
            //调用dubbo服务器批量导入预约设置信息
            orderSettingService.setNumberByDateBatch(orderSettings);
            return Result.success("");

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("");
        }


    }

    @RequestMapping("/findByMouth")
    public Result findByMouth(String mouth){
        List<Map> maps = orderSettingService.findByMouth(mouth);
        return Result.success("",maps);

    }
}
