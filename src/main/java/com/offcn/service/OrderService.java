package com.offcn.service;

import com.offcn.bean.Result;

import java.util.Map;

public interface OrderService {
    Result submitOrder(Map map);

    Map findOrderById(Integer id);
}
