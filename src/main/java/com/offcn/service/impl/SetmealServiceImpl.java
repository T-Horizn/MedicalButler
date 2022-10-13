package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.bean.*;
import com.offcn.mapper.*;
import com.offcn.service.SetmealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Autowired private SetmealMapper setmealMapper;

    @Autowired private CheckgroupMapper checkgroupMapper;

    @Autowired private CheckGroupItemMapper checkGroupItemMapper;

    @Autowired private CheckitemMapper checkitemMapper;


    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
      //保存套餐
        setmealMapper.insert(setmeal);
        //保存我们的套餐和检查组中的中间表
        for(Integer gid:checkgroupIds){
            SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
            setmealCheckgroup.setCheckgroup_id(gid);
            setmealCheckgroup.setSetmeal_id(setmeal.getId());

            //插入Mapper
            setmealCheckgroupMapper.insert(setmealCheckgroup);

        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Setmeal> page1=new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Setmeal> qw=new QueryWrapper<>();
        String mark=queryPageBean.getQueryString();
        if(StringUtils.isNotEmpty(mark))
        {
            qw.like("name",mark);
            qw.or();
            qw.like("code",mark);
            qw.or();
            qw.like("helpcode",mark);
        }
        Page<Setmeal> setmealPage=setmealMapper.selectPage(page1,qw);
        return new PageResult(setmealPage.getTotal(),setmealPage.getRecords());
    }

    @Override
    public void deleteInfoById(Integer id) {
        //删除中间表的数据
        //构建一个Map集合


        HashMap<String,Object> map=new HashMap<>();
        map.put("setmeal_id",id);
        setmealCheckgroupMapper.deleteByMap(map);

        //删除检查组表的数据
        setmealMapper.deleteById(id);
    }

    @Override
    public Setmeal findmealInfoById(Integer id) {
        return setmealMapper.selectById(id);
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return setmealMapper.selectList(null);
    }

    @Override
    public Setmeal findInfoById(Integer id) {
        //根据套餐id查询套餐信息
        Setmeal setmeal = setmealMapper.selectById(id);
        //根据套餐id从中间表中查询检查组的id
        LambdaQueryWrapper<SetmealCheckgroup> qw1 = new LambdaQueryWrapper();
        qw1.eq(SetmealCheckgroup::getSetmeal_id,id);

        qw1.select(SetmealCheckgroup::getCheckgroup_id);
        List <Integer> groupIds= setmealCheckgroupMapper.selectObjs(qw1).stream().map((x)->(Integer )x).collect(Collectors.toList());
        //批量查询检查组信息
        List<Checkgroup> checkgroups = checkgroupMapper.selectBatchIds(groupIds);
        //查询集合
        for (Checkgroup checkgroup : checkgroups) {
            //根据检查组的id查询中间表，   获取检查项的id
            LambdaQueryWrapper<CheckGroupItem>  qw2= new LambdaQueryWrapper<>();
            qw2.eq(CheckGroupItem::getCheckgroup_id,checkgroup.getId());
            qw2.select(CheckGroupItem::getCheckitem_id);
            List<Integer> itemIds=checkGroupItemMapper.selectObjs(qw2).stream().map((x)->(Integer)x).collect(Collectors.toList());
            //根据itemIds批量查询检查项的详细信息
            List<Checkitem> checkitems=checkitemMapper.selectBatchIds(itemIds);
            checkgroup.setCheckitems(checkitems);


        }
        setmeal.setCheckgroups(checkgroups);
        return setmeal;
    }

}
