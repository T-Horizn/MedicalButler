package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_checkgroup")
public class Checkgroup {
   private Integer id;
   private String  code;
   private String  name;
   private String  helpcode;
   private String  sex;
   private String  remark;
   private String  attention;


   @TableField(exist=false)
   private List<Checkitem> checkitems=new ArrayList<>();
}
