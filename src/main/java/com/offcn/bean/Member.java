package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("t_member")
public class Member {
    private Integer id;	//int
    private String filenumber;	//varchar
    private String name;	//varchar
    private String sex;	//varchar
    private String idcard;	//varchar
    private String phonenumber;	//varchar
    private LocalDate regtime;	//date
    private String password;	//varchar
    private String email;	//varchar
    private LocalDate birthday;	//date
    private String remark;	//varchar

}