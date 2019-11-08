package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (CollectionUtil.isNotEmpty(checkgroupIds)) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealMapper.setCheckGroupAndSetmealRelation(checkgroupId, id);
            }
        }
    }

    @Override
    public PageResult findpage(QueryPageBean queryPageBean) {

        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> setmeals = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), setmeals);
    }

    @Override
    public List<Setmeal> getSetmeal() {

        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        //根据id查询套餐详情
        Setmeal setmeal = setmealMapper.findById(id);
        if (null != setmeal) {
            //根据套餐id查询该套餐的检查组
            List<CheckGroup> checkGroups = setmealMapper.findCheckGroupBySetmealId(id);
            if (CollectionUtil.isNotEmpty(checkGroups)) {
                for (CheckGroup checkGroup : checkGroups) {
                    //根据所有检查组查询所有检查项
                   List<CheckItem> checkItems =  setmealMapper.findCheckItemByCheckGroupId(checkGroup.getId());
                   checkGroup.setCheckItems(checkItems);
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch(Integer id) {

        //根据id查询套餐详情
        Setmeal setmeal = setmealMapper.findById(id);
        if (null != setmeal) {
            //根据套餐id查询该套餐的检查组
            List<CheckGroup> checkGroups = setmealMapper.findCheckGroupBySetmealId(id);
            if (CollectionUtil.isNotEmpty(checkGroups)) {

                List<Integer> checkGroupIds = getCheckGroupIds(checkGroups);
                //把检查组id集合作为参数批量查询
                List<CheckItem> checkItems = setmealMapper.findCheckItemsByCheckGroupIdBatch(checkGroupIds);

                Map<Integer,List<CheckItem>> map = new HashMap<>();
                //遍历所有的检查组
                for (CheckGroup checkGroup : checkGroups) {
                    Integer checkGroupId = checkGroup.getId();

                    //遍历所有的检查项,与当前检查组进行匹配,满足就装入集合

                    List<CheckItem> checkItems2 = new ArrayList<>();
                    for (CheckItem checkItem : checkItems) {
                        if (checkItem.getCheckGroupId().equals(checkGroupId)){
                            checkItems2.add(checkItem);
                        }
                    }


                    map.put(checkGroupId,checkItems2);
                }

                for (CheckGroup checkGroup : checkGroups) {
                    List<CheckItem> checkItems1 = map.get(checkGroup.getId());
                    checkGroup.setCheckItems(checkItems1);
                }
            }

            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    /**
     * 上边的代码思路:
     *     首先,两个方法是为了获得查询的到的套餐,以及套餐中的检查组,和检查组对应的检查项,
     *   上边的代码,先查询获得了套餐中的所有检查组,和所有检查组对应的所有的检查项,然后在
     *   遍历检查组和检查项,获得检查组对应的检查项,该方法的查询次数为   检查组数*检查项数
     *
     * 下边的代码思路
     *     同样是先查询到所有的检查组,然后根据检查组查询到所有的检查项,遍历所有的检查项,
     *   将属于该检查组的检查项放入集合,该方法的查询次数为   检查项次数
     *
     *
     *
     * */

    @Override
    public Setmeal findByIdBatch58(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealMapper.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
            List<CheckGroup> checkGroups = setmealMapper.findCheckGroupBySetmealId(id);
            if(CollectionUtil.isNotEmpty(checkGroups)){
                //把检查组id集合作为参数批量查询
                List<Integer> ids = getCheckGroupIds(checkGroups);
                List<CheckItem> checkItems = setmealMapper.findCheckItemsByCheckGroupIdBatch(ids);

                Map<Integer, List<CheckItem>> map = new HashMap<>();

                for (CheckItem checkItem : checkItems) {
                    Integer checkGroupId = checkItem.getCheckGroupId();
                    List<CheckItem> list = map.get(checkGroupId);
                    if(null == list){
                        list = new ArrayList<>();
                        map.put(checkGroupId,list);
                    }

                    list.add(checkItem);
                }

                //分组
                for (CheckGroup checkGroup : checkGroups) {
                    checkGroup.setCheckItems(map.get(checkGroup.getId()));
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }
    private List<Integer> getCheckGroupIds(List<CheckGroup> checkGroups) {

        List<Integer> checkGroupIds = new ArrayList<>();
        for (CheckGroup checkGroup : checkGroups) {
            checkGroupIds.add(checkGroup.getId());
        }
        return checkGroupIds;
    }

    public Setmeal findByIdBatch58Tanzhe(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealMapper.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
            List<CheckGroup> checkGroups = setmealMapper.findCheckGroupBySetmealId(id);
            if(CollectionUtil.isNotEmpty(checkGroups)){
                //把检查组id集合作为参数批量查询
                List<Integer> ids = getCheckGroupIds(checkGroups);

                List<CheckItem> checkItems = setmealMapper.findCheckItemsByCheckGroupIdBatch(ids);

                Map<Integer, List<CheckItem>> map = new HashMap<>();

                map = checkItems
                        .stream()
                        .collect(Collectors.groupingBy(CheckItem::getCheckGroupId));

                //分组
                for (CheckGroup checkGroup : checkGroups) {
                    checkGroup.setCheckItems(map.get(checkGroup.getId()));
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

}
