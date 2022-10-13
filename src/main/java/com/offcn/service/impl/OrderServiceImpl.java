package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.offcn.bean.*;
import com.offcn.config.MessageConstant;
import com.offcn.mapper.MemberMapper;
import com.offcn.mapper.OrderMapper;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.mapper.SetmealMapper;
import com.offcn.service.OrderService;
import com.offcn.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Result submitOrder(Map map) {
        try {
            //1.根据日期查询预约设置表是否可预约
            String orderDate = (String)map.get("orderDate");
            LambdaQueryWrapper<Ordersetting> qw1 = new LambdaQueryWrapper<>();
            qw1.eq(Ordersetting::getOrderdate,orderDate);
            Ordersetting os = ordersettingMapper.selectOne(qw1);
            //2.判断设置表是否有数据
            if(os==null){
                return new Result(false, "预约失败");
            }
            //3.判断预约人数是否满足人数
            if(os.getReservations()>=os.getNumber()){
                return new Result(false,MessageConstant.ORDER_FULL);
            }

            //4.查看是否是新用户
            String telephone=(String)map.get("telephone");
            LambdaQueryWrapper<Member> qw2 = new LambdaQueryWrapper<Member>();
            qw2.eq(Member::getPhonenumber,telephone);

            Member member=memberMapper.selectOne(qw2);


            memberMapper.selectOne(qw2);
            //判断
            if(member==null){
                //把用户数据存入到用户信息表中
                member=new Member();
                member.setName((String )map.get("name"));
                member.setSex((String)map.get("sex"));
                member.setIdcard((String)map.get("idCard"));
                member.setPhonenumber((String)map.get("telephone"));
                LocalDate date=new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                member.setRegtime(date);

                memberMapper.insert(member);



            }

            //5.把订单数据插入到订单表里
            Order order=new Order();
            order.setMember_id(member.getId());
            String datex=(String)map.get("orderDate");
            LocalDate datey=DateUtils.parseString2Date(datex).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            order.setOrderdate(datey);
            order.setOrdertype((String)map.get("orderType"));
            order.setOrderstatus("未到诊所");
            order.setSetmeal_id(Integer.valueOf((String)map.get("setmealId")));


            //增加订单信息
            orderMapper.insert(order);

            //更新预约设置表中的预约人数+1
            os.setReservations(os.getReservations()+1);
            ordersettingMapper.update(os,qw1);
            return new Result(true,"预约成功",order.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"预约失败");
        }
    }

    @Override
    public Map findOrderById(Integer id) {
        //1.根据Id查询order中的数据
        Order order = orderMapper.selectById(id);
        //2.根据用户的id查询用户的信息
        Member member=memberMapper.selectById(order.getMember_id());
        //3.根据套餐id查询套餐信息
        Setmeal setmeal=setmealMapper.selectById(order.getSetmeal_id());
        //4.组装数据
         HashMap<String,Object> map=new HashMap<>();
         map.put("member",member.getName());
         map.put("setmeal",setmeal.getName());
         map.put("orderDate",order.getOrderdate());
         map.put("orderType",order.getOrdertype());

        return map;
    }


}
