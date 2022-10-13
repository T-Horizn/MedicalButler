package com.offcn.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("t_setmeal_checkgroup")
public class SetmealCheckgroup {
        private Integer setmeal_id;
        private Integer checkgroup_id;

}
