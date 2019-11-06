package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupMapper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupMapper checkGroupMapper;

    @Autowired
    CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckGroup checkGroup) {

        //检查组的基本信息要插入到t_checkgroup(插入之后要返回主键)
        checkGroupMapper.add(checkGroup);

        Integer GroupId = checkGroup.getId();

        //检查组要和检查项建立关系
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();

        setCheckItemAndCheckGroupRelation(checkitemIds,GroupId);

    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<CheckGroup> checkGroups = checkGroupMapper.findPage(queryPageBean.getQueryString());

        return PageResult.builder()
                .rows(checkGroups)
                .total(page.getTotal())
                .build();
    }

    @Override
    public Map findById4Update(Integer id) {

        //根据检查组的id查询检查组详情
        CheckGroup checkGroup = checkGroupMapper.findById(id);

        //查询所有的检查项
        List<CheckItem> allCheckItems = checkItemMapper.findAll();

        //根据检查组的id查询检查组下面的所有检查项id集合
        List<Integer> checkItemIds = checkGroupMapper.findCheckItemIdsByCheckGroup(id);

        Map map = new HashMap();
        map.put("checkGroup",checkGroup);
        map.put("allCheckItems",allCheckItems);
        map.put("checkItemIds",checkItemIds);

        return map;

    }

    @Override
    public void edit(CheckGroup checkGroup) {

        //更新检查组的基本信息
        checkGroupMapper.edit(checkGroup);
        //删除原来的关系
        checkGroupMapper.deleteAssociation(checkGroup.getId());
        //建立新的联系
        setCheckItemAndCheckGroupRelation(checkGroup.getCheckitemIds(),checkGroup.getId());
    }

    @Override
    public List<CheckGroup> findAll() {

        return checkGroupMapper.findAll();
    }

    //根据查询的到的检查项主键和检查组的主键
    //将检查项和检查组在关系表中建立关系
    //有多个方法需用到该方法,所以将其封装
    private void setCheckItemAndCheckGroupRelation(List<Integer> checkitemIds,Integer GroupId){
        if(CollectionUtil.isNotEmpty(checkitemIds)){
            //循环插入
//            for (Integer checkitemId : checkitemIds) {
//                checkGroupMapper.setCheckItemAndCheckGroupRelation(checkitemId,GroupId);
//            }

            //批量插入
            List<Map> params = new ArrayList<>();
            for(Integer checkitemId : checkitemIds){
                Map map = new HashMap<>();
                map.put("checkgroup_id",GroupId);
                map.put("checkitem_id",checkitemId);
                params.add(map);
            }
            checkGroupMapper.setCheckItemAndCheckGroupRelation2(params);
        }

    }
}
