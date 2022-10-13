package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_setmeal")
public class Setmeal {
    private Integer id;
    private String name;
    private String code;
    private String helpcode;
    private String sex;
    private String age;
    private Float price;
    private String remark;
    private String attention;
    private String img;

    @TableField(exist=false)
    private List<Checkgroup> checkgroups=new ArrayList<>();
}
