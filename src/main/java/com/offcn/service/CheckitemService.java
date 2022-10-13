package com.offcn.service;

import com.offcn.bean.Checkitem;
import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;

import java.util.List;

/**
 * @author zh
 * @Description TODO
 * @createTime 2022/8/15
 */
public interface CheckitemService {
    void checkItemSave(Checkitem checkitem);

    PageResult pageQuery(QueryPageBean bean);

    void deleteInfoById(Integer id);

    Checkitem findInfoById(Integer id);

    void updateInfoById(Checkitem checkitem);


    List<Checkitem> findAll();


}
