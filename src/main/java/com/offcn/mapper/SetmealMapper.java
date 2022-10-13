package com.offcn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.offcn.bean.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper
public interface SetmealMapper extends BaseMapper <Setmeal> {

}
