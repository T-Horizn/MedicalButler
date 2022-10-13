package com.offcn.service;

import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;
import com.offcn.bean.Setmeal;

import java.util.List;

public interface SetmealService {
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteInfoById(Integer id);

    Setmeal findmealInfoById(Integer id);

    List<Setmeal> getAllSetmeal();

    Setmeal findInfoById(Integer id);
}
