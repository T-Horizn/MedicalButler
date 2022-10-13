package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zh
 * @Description TODO
 * @createTime 2022/8/15
 */
@Data
@TableName("t_checkitem")
public class Checkitem {
    private Integer id;	//int
    private String code;	//varchar
    private String name;	//varchar
    private String sex;	//char
    private String age;	//varchar
    private Float price;	//float
    private String type;	//char
    private String attention;	//varchar
    private String remark;	//varchar

}
