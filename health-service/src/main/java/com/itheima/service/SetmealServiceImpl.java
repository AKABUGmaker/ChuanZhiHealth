package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public void add(Setmeal setmeal) {

        setmealMapper.add(setmeal);

        Integer id = setmeal.getId();

        List<Integer> checkgroupIds = setmeal.getCheckgroupIds();

        if(CollectionUtil.isNotEmpty(checkgroupIds)){
            for (Integer checkgroupId : checkgroupIds) {
                setmealMapper.setCheckGroupAndSetmealRelation(checkgroupId,id);
            }
        }
    }

    @Override
    public PageResult findpage(QueryPageBean queryPageBean) {

        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> setmeals = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),setmeals);
    }

}
