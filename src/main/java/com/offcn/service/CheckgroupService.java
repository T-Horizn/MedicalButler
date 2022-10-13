package com.offcn.service;

import com.offcn.bean.Checkgroup;
import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;

import java.util.List;

public interface CheckgroupService {
    void saveGroupAndItems(Checkgroup checkgroup, Integer[] checkitemIds);
    PageResult findPage(QueryPageBean queryPageBean);


    Checkgroup findGroupInfoById(Integer id);

    void editGroup(Checkgroup checkgroup, Integer[] checkitemIds);

    void deleteInfoById(Integer id);

    List<Checkgroup> showAllGroupInfo();
}
