package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;


@Data
@TableName("t_order")
public class Order {
   private Integer  id;
   private Integer  member_id;
   private LocalDate orderdate;
    private String ordertype;
    private String orderstatus;
    private Integer setmeal_id;
}
