package com.offcn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.offcn.bean.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
