package com.offcn.controller;

import com.offcn.bean.Ordersetting;
import com.offcn.bean.Result;
import com.offcn.config.MessageConstant;
import com.offcn.service.OrdersettingService;
import com.offcn.utils.POIUtils;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RequestMapping("ordersetting")
@RestController
public class OrdersettingController {

    @Autowired
    private OrdersettingService ordersettingService;

    @PostMapping("uploadTempleate")
    public Result uploadTempleate(@RequestParam("excelFile")MultipartFile multipartFile){
        //读取上传文件的内容
        try {
            List<String[]> strings = POIUtils.readExcel(multipartFile);

            ordersettingService.saveOrderSetting(strings);

            return new Result(true, MessageConstant.UPLOAD_SUCCESS,strings);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }

    @PostMapping("/getOrdersettingByDate")
    public Result getOrdersettingByDate(String date) {
        try {
            List<Map> map=ordersettingService.getOrderssettingByDate(date);
            return new Result(true,"查询成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询失败" );
        }


    }
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Ordersetting ordersetting){
        try {
            ordersettingService.editNumberByDate(ordersetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }

}
