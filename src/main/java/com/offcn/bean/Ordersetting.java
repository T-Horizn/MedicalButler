package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("t_ordersetting")
public class Ordersetting {
    private Integer id;
    private LocalDate orderdate;
    private Integer number;
    private Integer reservations;
}
