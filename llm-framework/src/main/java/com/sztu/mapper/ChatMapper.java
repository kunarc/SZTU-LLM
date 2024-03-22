package com.sztu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sztu.entity.Chat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
}
