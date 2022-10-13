package com.offcn.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.offcn.bean.Checkgroup;
import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;
import com.offcn.bean.Result;
import com.offcn.config.MessageConstant;
import com.offcn.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("checkgroup")
public class CheckgroupController {
    @Autowired
    private CheckgroupService checkgroupService;

    @PostMapping("/addGroup")
    public Result addGroup(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        try {
            checkgroupService.saveGroupAndItems(checkgroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);

        }








    }

     @RequestMapping("/findPage")
  @ResponseBody
  public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
      PageResult pageResult = checkgroupService.findPage(queryPageBean);
      return pageResult;
  }
  @GetMapping("/findGroupInfoById")
    public Result findGroupInfoById(Integer id){
      try {
          Checkgroup checkgroup=checkgroupService.findGroupInfoById(id);
          return new Result(true,"查询检查组信息成功",checkgroup);
      } catch (Exception e) {
          e.printStackTrace();
          return new Result(false,"查询检查组信息失败");
      }

  }


    @PostMapping("/editGroup")
    public Result editGroup(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        try {
            checkgroupService.editGroup(checkgroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL);

        }

    }
    @GetMapping("/deleteInfoById")
    public Result deleteInfoById(Integer id){
        try {
            checkgroupService.deleteInfoById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }

    @GetMapping("/showAllGroupInfo")
    public Result  showAllGroupInfo(){

        try {
            List<Checkgroup> checkgroups =checkgroupService.showAllGroupInfo();
            return new Result(true,"查询成功",checkgroups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询失败");
        }
    }



    

}
