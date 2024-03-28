package com.sztu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sztu.dto.ChatDetailsDto;
import com.sztu.entity.Chat;
import com.sztu.entity.ChatDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatDetailsMapper extends BaseMapper<ChatDetails> {

    List<Long> criticalSearch(@Param("chatDetailsDto")ChatDetailsDto chatDetailsDto);
}
