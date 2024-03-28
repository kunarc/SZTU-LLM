package com.sztu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sztu.entity.Chat;
import com.sztu.entity.CommonQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonQuestionMapper extends BaseMapper<CommonQuestion> {

}
