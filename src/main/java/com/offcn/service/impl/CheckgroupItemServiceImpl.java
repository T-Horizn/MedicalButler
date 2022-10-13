package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.offcn.bean.CheckGroupItem;
import com.offcn.bean.Checkgroup;
import com.offcn.mapper.CheckGroupItemMapper;
import com.offcn.service.CheckgroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CheckgroupItemServiceImpl implements CheckgroupItemService {
    @Autowired
    private CheckGroupItemMapper checkgroupItemMapper;

    @Override
    public List<Integer> findGroupItemCheck(Integer id) {
        LambdaQueryWrapper<CheckGroupItem> qw = new LambdaQueryWrapper<>();
        //select item_id  from 中间表 where group_id = 1;
        qw.eq(CheckGroupItem::getCheckgroup_id,id);
        qw.select(CheckGroupItem::getCheckitem_id);
        List<Object> objects = checkgroupItemMapper.selectObjs(qw);
        List<Integer> collect = objects.stream().map((x) -> (Integer) x).collect(Collectors.toList());
        return collect;
    }
}
