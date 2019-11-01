package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Test;
import com.itheima.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Reference
    TestService testService;

    @RequestMapping("/findAll")
    public List<Test> findAll(){
        return testService.findAll();
    }
}
