package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.bean.CheckGroupItem;
import com.offcn.bean.Checkitem;
import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;
import com.offcn.mapper.CheckGroupItemMapper;
import com.offcn.mapper.CheckitemMapper;
import com.offcn.service.CheckitemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zh
 * @Description TODO
 * @createTime 2022/8/15
 */
@Service
public class CheckItemServiceImpl implements CheckitemService {
    @Autowired
    private CheckitemMapper checkitemMapper;
    @Override
    public void checkItemSave(Checkitem checkitem) {
        checkitemMapper.insert(checkitem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean bean) {
        //判断搜索框的内容
        String queryString = bean.getQueryString();
        //构造查询条件
        LambdaQueryWrapper<Checkitem> qw = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(queryString)){
            //select * from checkitem where code like ? or name like ?
            qw.like(Checkitem::getCode, queryString).or().like(Checkitem::getName, queryString);
        }
        qw.orderByDesc(Checkitem::getId);
        Page<Checkitem> page = new Page<>(bean.getCurrentPage(), bean.getPageSize());
        Page<Checkitem> checkitemPage = checkitemMapper.selectPage(page, qw);
        PageResult pageResult = new PageResult(checkitemPage.getTotal(), checkitemPage.getRecords());
        return pageResult;
    }
    @Override
    public  void deleteInfoById(Integer id)
    {
        checkitemMapper.deleteById(id);
    }

    @Override
    public Checkitem findInfoById(Integer id) {
        return checkitemMapper.selectById(id);
    }
    @Override
    public void updateInfoById(Checkitem checkitem) {
        checkitemMapper.updateById(checkitem);
    }
    @Override
    public List<Checkitem> findAll() {
        List<Checkitem> checkitems = checkitemMapper.selectList(null);
        return checkitems;
    }




}
