package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    /**
     * 页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * List 不管名字一不一样 必须加@RequestParam
     * @return
     */

    @Reference
    CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup){
        checkGroupService.add(checkGroup);
        return Result.success("");
    }

    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }

    @RequestMapping("/findById4Update")
    public Result findById4Update(Integer id){
        Map map = checkGroupService.findById4Update(id);
        return Result.success("",map);
    }


    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup){
        checkGroupService.edit(checkGroup);
        return Result.success("");
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        return Result.success("",checkGroups);
    }

}
