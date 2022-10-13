package com.offcn.controller;

import com.offcn.bean.Result;
import com.offcn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submitOrder")
    //[orderDate:date,idCard:shenfenzheng]
    public Result submitOrder(@RequestBody Map map){
        map.put("orderType","微信预约");
        Result result=orderService.submitOrder(map);

        System.out.println(map);
        return result;
    }


     @PostMapping("/findOrderById")
     public Result findOrderById(Integer id){
         try {
             Map map=orderService.findOrderById(id);
             return new Result(true,"查询成功",map);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false,"查询失败");
         }
     }
}
