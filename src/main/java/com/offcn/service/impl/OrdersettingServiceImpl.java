package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.offcn.bean.Ordersetting;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.service.OrdersettingService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void saveOrderSetting(List<String[]> strings) {

        //[[2021/10/11,100]
        //解析参数
        if(strings!= null&&strings.size()>0){

            for (String[] string : strings) {
                //[2021/10/21/,200]
                //转换日期
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(string[0], dateTimeFormatter);
                //处理可预约的数量

                Integer number = Integer.valueOf(string[1]);

                //放入bean当中
                Ordersetting os = new Ordersetting();
                os.setOrderdate(date);
                os.setNumber(number);

                //查询数据库中是否存在当前日期的数据
                LambdaQueryWrapper<Ordersetting> qw1 = new LambdaQueryWrapper<>();
                qw1.eq(Ordersetting::getOrderdate,date);
                Ordersetting oos = ordersettingMapper.selectOne(qw1);

                //如果不存在  增加

                if(oos==null){
                        ordersettingMapper.insert(os);
                }else{
                    //如果存在  更新
                    ordersettingMapper.update(os,qw1);
                }





            }
        }
    }

    @Override
    public List<Map> getOrderssettingByDate(String date) {
        //2021-08
        String startDate = date+"-01";//2021-08-01
        String endDate= date+"-31";//2021-08-31
        LambdaQueryWrapper<Ordersetting> qw = new LambdaQueryWrapper<>();
        qw.between(Ordersetting::getOrderdate,startDate,endDate);
        List<Ordersetting> os = ordersettingMapper.selectList(qw);

        ArrayList<Map> maps=new ArrayList<>();
        //把List转换为List<Map>
        os.forEach((x)->{
            //x就是List集合遍历后的每一个Orderseting数据
             HashMap<String, Object> hm=new HashMap<>();
             hm.put("date",x.getOrderdate().getDayOfMonth());
             hm.put("number",x.getNumber());
             hm.put("reservations",x.getReservations());
            maps.add(hm);

        });
        return maps;
    }

    @Override
    public void editNumberByDate(Ordersetting ordersetting) {
        //根据日期查询数据库中是否存在当前日期的数据
        LambdaQueryWrapper<Ordersetting> qw = new LambdaQueryWrapper<>();
        qw.eq(Ordersetting::getOrderdate,ordersetting.getOrderdate());


        //查询
        Ordersetting os = ordersettingMapper.selectOne(qw);
        //判断
        if(os!=null){
            //有数据  更新
            ordersettingMapper.update(ordersetting,qw);
        }else{
            //没有数据  增加
            ordersettingMapper.insert(ordersetting);
        }
    }
}
