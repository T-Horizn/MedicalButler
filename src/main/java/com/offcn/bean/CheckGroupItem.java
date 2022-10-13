package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_checkgroup_checkitem")
public class CheckGroupItem {
        private Integer checkgroup_id;
        private Integer checkitem_id;
}
