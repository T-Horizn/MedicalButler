package com.offcn.controller;

import com.offcn.bean.Checkitem;
import com.offcn.bean.PageResult;
import com.offcn.bean.QueryPageBean;
import com.offcn.bean.Result;
import com.offcn.config.MessageConstant;
import com.offcn.service.CheckgroupItemService;
import com.offcn.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zh
 * @Description TODO
 * @createTime 2022/8/15
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {
    @Autowired
    private CheckitemService checkitemService;

    @Autowired
    private CheckgroupItemService checkgroupItemService;

    @PostMapping("/add")
    public Result checkItemAdd(@RequestBody Checkitem checkitem){
        try {
            checkitemService.checkItemSave(checkitem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }


    @PostMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean bean){
        PageResult pageResult = checkitemService.pageQuery(bean);
        return pageResult;
    }
    @RequestMapping("/deleteInfoById")
    @ResponseBody
    public Result deleteInfoById(Integer id){
        System.out.println(id);
        try {
            checkitemService.deleteInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false ,MessageConstant.DELETE_CHECKGROUP_FAIL);
                    }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    //回显检查项数据
    @RequestMapping("/findInfoById")
    @ResponseBody
    public Result findInfoById(Integer id){
        Checkitem infoById = null;
        try{
            infoById = checkitemService.findInfoById(id);
        }catch (Exception e){
            e.printStackTrace();
            new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS,infoById);
    }
    //更新检查项数据
    @RequestMapping("/updateInfoById")
    @ResponseBody
    public Result updateInfoById(@RequestBody Checkitem checkitem){
        try{
            checkitemService.updateInfoById(checkitem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    //查询所有检查项的信息
    @GetMapping("/showAllItem")
    public Result showAllItem(){
        try {
            List<Checkitem> checkitems = checkitemService.findAll();

            return new Result(true,"检查项查询成功", checkitems);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"检查项查询失败"  );


        }

    }
    @GetMapping("/getCheckItemIdByGroupId")
    public  List<Integer> getCheckItemIdByGroupId(Integer id){
       List<Integer> ids= checkgroupItemService.findGroupItemCheck(id);

       return ids;

    }


}
