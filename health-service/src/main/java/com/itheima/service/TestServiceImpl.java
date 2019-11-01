package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.TestMapper;
import com.itheima.pojo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TestService.class)
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public List<Test> findAll() {
        return testMapper.findAll();
    }
}
