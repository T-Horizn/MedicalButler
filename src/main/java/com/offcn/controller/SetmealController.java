package com.offcn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.offcn.bean.*;
import com.offcn.config.MessageConstant;
import com.offcn.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    //MultipartFile :存放着上传图片的所有信息
    @RequestMapping("/uploadpic")
    public Result uploadpic(@RequestParam("imgFile") MultipartFile multipartFile){
        //获取当前文件的名称
        String originalFilename = multipartFile.getOriginalFilename();
        //1.png
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件后缀 找文件最后一个点 保留后缀保住前面的
        String suffix = originalFilename.substring(lastIndexOf - 1);

        String fileName  = UUID.randomUUID().toString()+suffix;
        //定义一个文件上传的位置
        File file = new File("D:/upload/"+fileName);
        try {
            //上传图片
            multipartFile.transferTo(file);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);



        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }
    @RequestMapping("/addSetmeal")
    public  Result addSetmeal(@RequestBody Setmeal setmeal, Integer [] checkgroupIds){
        try {
            setmealService.addSetmeal(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }
    //分页展示套餐内容
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/deleteInfoById")
    public Result deleteInfoById(Integer id){
        System.out.println(id);
        try{
            setmealService.deleteInfoById(id);
        }catch (Exception e){
            e.printStackTrace();
            new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    @GetMapping("/findmealInfoById")
    public Result findGroupInfoById(Integer id){
        try {
            Setmeal setmeal=setmealService.findmealInfoById(id);
            return new Result(true,"查询检查组信息成功",setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询检查组信息失败");
        }

    }

    @RequestMapping("/getAllSetmeal")
    public  Result getAllSetmeal(){
        List<Setmeal> setmealList = null;
        try {
            setmealList = setmealService.getAllSetmeal();
            return  new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,setmealList);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
    @PostMapping("/findInfoById")
    public Result findInfoById(Integer id){
        try {
            Setmeal setmeal=setmealService.findInfoById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }




}
