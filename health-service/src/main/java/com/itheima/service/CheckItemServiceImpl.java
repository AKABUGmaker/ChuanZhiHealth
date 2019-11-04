package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<CheckItem> checkItems = checkItemMapper.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(), checkItems);
    }

    @Override
    public CheckItem findById(Integer id) {

        return checkItemMapper.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.edit(checkItem);
    }

    @Override
    public void delete(Integer id) {

        //根据检查项的id查询当前项目是否被引用
        Integer countById = checkItemMapper.findCountById(id);
        if (null != countById && countById > 0) {
            //如果有引用就提示用户
            throw new CheckItemException("存在引用不能删除用户");
        } else {
            //没有引用直接删除
            checkItemMapper.delete(id);
        }
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }
}
