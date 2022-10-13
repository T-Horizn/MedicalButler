package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.bean.*;
import com.offcn.mapper.CheckGroupItemMapper;
import com.offcn.mapper.CheckgroupMapper;
import com.offcn.service.CheckgroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CheckgroupServiceImpl implements CheckgroupService {
     @Autowired
     private CheckgroupMapper checkgroupMapper;



     @Autowired
     private CheckGroupItemMapper checkGroupItemMapper;
    @Override
    public void saveGroupAndItems(Checkgroup checkgroup, Integer[] checkitemIds) {
        //保存检查组所有信息
        checkgroupMapper.insert(checkgroup);
        //在中间表中插入检查组合检查项的对应信息
        for(Integer x:checkitemIds){
            CheckGroupItem cg = new CheckGroupItem();
            cg.setCheckgroup_id(checkgroup.getId());
            cg.setCheckitem_id(x);


            checkGroupItemMapper.insert(cg);

        }

    }

   /* @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //构建分页page对象
        Page<Checkgroup> page1 = new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //构建查询条件对象
        QueryWrapper<Checkgroup> wrapper = new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null &&queryPageBean.getQueryString().length()>0){
            wrapper.like("name",queryPageBean.getQueryString());
            wrapper.or();
            wrapper.like("code",queryPageBean.getQueryString());
            wrapper.like("helpcode",queryPageBean.getQueryString());

        }
        //分页查询数据
        Page<Checkgroup> checkgroupPage = checkgroupMapper.selectPage(page1, wrapper);
        return new PageResult(checkgroupPage.getTotal(),checkgroupPage.getRecords());
    }*/
    @Override
    public PageResult findPage(QueryPageBean bean) {
        //判断搜索框的内容
        String queryString = bean.getQueryString();
        //构造查询条件
        LambdaQueryWrapper<Checkgroup> qw = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(queryString)){
            //select * from checkitem where code like ? or name like ?
            qw.like(Checkgroup::getCode, queryString).or().like(Checkgroup::getName, queryString).or().like(Checkgroup::getHelpcode,queryString);
        }
        qw.orderByAsc(Checkgroup::getId);
        Page<Checkgroup> page = new Page<>(bean.getCurrentPage(), bean.getPageSize());
        Page<Checkgroup> checkgroupPage = checkgroupMapper.selectPage(page, qw);
        PageResult pageResult = new PageResult(checkgroupPage.getTotal(), checkgroupPage.getRecords());
        return pageResult;
    }

    @Override
    public Checkgroup findGroupInfoById(Integer id) {
        Checkgroup checkgroup = checkgroupMapper.selectById(id);
        return checkgroup;
    }

    @Override
    public void editGroup(Checkgroup checkgroup, Integer[] checkitemIds) {
        //更新检查组信息
        checkgroupMapper.updateById(checkgroup);
        //把中间表中所有跟当前检查组相关的检查项信息删除
         LambdaQueryWrapper<CheckGroupItem> qw=new LambdaQueryWrapper<>();
        qw.eq(CheckGroupItem::getCheckgroup_id,checkgroup.getId());
        checkGroupItemMapper.delete(qw);
        //在中间表中加入选中的检查项信息

        for (Integer checkitemId : checkitemIds) {
            CheckGroupItem checkGroupItem = new CheckGroupItem();
            checkGroupItem.setCheckgroup_id(checkgroup.getId());
            checkGroupItem.setCheckitem_id(checkitemId);
            checkGroupItemMapper.insert(checkGroupItem);
        }
    }

    @Override
    public void deleteInfoById(Integer id) {
      //删除中间表的数据
        //构建一个Map集合


      HashMap<String,Object> map=new HashMap<>();
       map.put("checkgroup_id",id);
       checkGroupItemMapper.deleteByMap(map);

      //删除检查组表的数据
        checkgroupMapper.deleteById(id);

    }

    @Override
    public List<Checkgroup> showAllGroupInfo() {
        List<Checkgroup> checkgroups=checkgroupMapper.selectList(null);

        return checkgroups;
    }


}
