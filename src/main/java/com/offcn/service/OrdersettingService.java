package com.offcn.service;

import com.offcn.bean.Ordersetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    void saveOrderSetting(List<String[]> strings);

    List<Map> getOrderssettingByDate(String date);

    void editNumberByDate(Ordersetting ordersetting);
}
