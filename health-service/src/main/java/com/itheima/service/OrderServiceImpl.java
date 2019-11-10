package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfoVo;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;
    @Override
    public Result submit(OrderInfoVo orderInfoVo) {

        Date orderDate = orderInfoVo.getOrderDate();
        String name = orderInfoVo.getName();
        Integer sex = orderInfoVo.getSex();
        String idCard = orderInfoVo.getIdCard();
        Integer setmealId = orderInfoVo.getSetmealId();
        String orderType = orderInfoVo.getOrderType();
        //根据用户选择的预约日期查询当前日期是否可以预约
        OrderSetting orderSetting = orderSettingMapper.findByDate(orderDate);
        if (null == orderSetting){
            return Result.error("没有该档期");
        }
        //判断该预约日期是否预约已满
        int number = orderSetting.getNumber();//总预约人数
        int reservations = orderSetting.getReservations();//已预约人数

        if(reservations >= number){
            return Result.error("预约已满");
        }
        //根据输入的手机号码查询是否是会员
        String telephone = orderInfoVo.getTelephone();
        Member member = memberDao.findByTelephone(telephone);

        //如果不是会员,自动帮其注册
        if (null == member){

            //如果等于null,说明没有member,需要先new一个
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setName(name);
            member.setSex(String.valueOf(sex));
            member.setIdCard(idCard);
            member.setRegTime(new Date());


            //如果要想得到id,sql需要主键回显
            memberDao.add(member);
        }

        //根据会员id,套餐id,预约日期查询是否已经预约过

        Integer memberId = member.getId();
        Order query = new Order();
        query.setMemberId(memberId);
        query.setSetmealId(setmealId);
        query.setOrderDate(orderDate);

        List<Order> orders = orderDao.findByCondition(query);

        if(CollectionUtil.isNotEmpty(orders)){
            return Result.error("不要重复预约");
        }

        //如果都通过,创建预约订单
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setMemberId(memberId);
        order.setSetmealId(setmealId);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderType);

        orderDao.add(order);
        //修改已预约人数+1
        //orderSettingMapper.updateReservation(orderSetting.getId());
        int i = orderSettingMapper.updateReservations(orderSetting.getId(),orderSetting.getVersion());

        if(i == 0){
            throw new RuntimeException();
        }
        //返回预约成功
        return Result.success("预约成功",order);

    }

    @Override
    public Map findById(Integer id) {

      return orderDao.findById4Detail(id);
    }
}
